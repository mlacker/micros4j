package com.lacker.micros.data.service.statement;

import com.lacker.micros.data.domain.schema.DataColumn;
import com.lacker.micros.data.domain.schema.DataTable;
import com.lacker.micros.data.domain.schema.TableRepository;
import com.lacker.micros.data.domain.statement.StatementVisitorAdapter;
import com.lacker.micros.domain.exception.InvalidOperationAppException;
import com.lacker.micros.domain.exception.InvalidParameterAppException;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class StetementTransformer extends StatementVisitorAdapter {

    private final TableRepository tableRepo;

    public StetementTransformer(TableRepository tableRepo) {
        this.tableRepo = tableRepo;
    }

    @Override
    protected boolean allowColumnProcessing() {
        return false;
    }

    @Override
    public void visit(Table tableName) {
        DataTable table = getTable(tableName.getName());
        tableName.setName("`" + table.getTableName() + "`");
    }

    @Override
    public void visit(Column tableColumn) {
        super.visit(tableColumn);
        if (tableColumn.getTable() == null) {
            throw new UnsupportedOperationException("column must have a table");
        }
        DataColumn column = getColumn(tableColumn.getTable().getName(), tableColumn.getColumnName());
        tableColumn.setColumnName("`" + column.getColumnName() + "`");
    }

    private DataTable getTable(String key) {
        Optional<DataTable> table = tableRepo.find(trimBackQuote(key));

        if (table.isEmpty()) {
            throw new InvalidOperationAppException("Illegal select statement, tableName: " + key);
        }

        return table.get();
    }

    private DataColumn getColumn(String tableId, String columnId) {
        DataTable table = getTable(tableId);

        DataColumn column = table.getColumn(trimBackQuote(columnId));

        if (column == null) {
            throw new InvalidOperationAppException("Illegal select statement, columnName: " + columnId);
        }

        return column;
    }

    private Long trimBackQuote(String key) {
        // "`table-id`" to "table-id"
        if (key.charAt(0) == '`' && key.charAt(key.length() - 1) == '`') {
            key = key.substring(1, key.length() - 1);
        }

        try {
            return Long.valueOf(key);
        } catch (NumberFormatException ex) {
            throw new InvalidParameterAppException("Illegal key: " + key);
        }
    }
}
