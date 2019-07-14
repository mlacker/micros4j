package com.lacker.micros.data.service;

import com.lacker.micros.data.domain.schema.Column;
import com.lacker.micros.data.domain.schema.Table;
import com.lacker.micros.data.domain.schema.TableRepository;
import com.lacker.micros.domain.exception.InvalidOperationAppException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SchemaTransformer extends StatementVisitorAdapter {

    private final TableRepository tableRepo;

    public SchemaTransformer(TableRepository tableRepo) {
        this.tableRepo = tableRepo;
    }

    @Override
    protected boolean allowColumnProcessing() {
        return false;
    }

    @Override
    public void visit(net.sf.jsqlparser.schema.Table tableName) {
        Table table = getTable(tableName.getName());
        tableName.setName("`" + table.getTableName() + "`");
    }

    @Override
    public void visit(net.sf.jsqlparser.schema.Column tableColumn) {
        super.visit(tableColumn);
        if (tableColumn.getTable() == null) {
            throw new UnsupportedOperationException("column must have a table");
        }
        Column column = getColumn(tableColumn.getTable().getName(), tableColumn.getColumnName());
        tableColumn.setColumnName("`" + column.getColumnName() + "`");
    }

    private Table getTable(String key) {
        Optional<Table> table = tableRepo.find(trimBackQuote(key));

        if (!table.isPresent()) {
            throw new InvalidOperationAppException("Illegal select statement, tableName: " + key);
        }

        return table.get();
    }

    private Column getColumn(String tableId, String columnId) {
        Table table = getTable(tableId);

        Column column = table.getColumn(trimBackQuote(columnId));

        if (column == null) {
            throw new InvalidOperationAppException("Illegal select statement, columnName: " + columnId);
        }

        return column;
    }

    private String trimBackQuote(String key) {
        // "`table-id`" to "table-id"
        if (key.charAt(0) == '`' && key.charAt(key.length() - 1) == '`') {
            key = key.substring(1, key.length() - 1);
        }
        return key;
    }
}
