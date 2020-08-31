package com.mlacker.micros.data.service;

import com.mlacker.micros.data.domain.schema.DataColumn;
import com.mlacker.micros.data.domain.schema.DataTable;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class TableBuilder {

    private final DataTable table;

    private TableBuilder(DataTable table) {
        this.table = table;
    }

    public static TableBuilder createTable (Long id, String name) {
        DataTable table = spy(DataTable.class);
        when(table.getId()).thenReturn(id);
        table.setTableName(name);
        return new TableBuilder(table);
    }

    public TableBuilder createColumn (Long id, String name) {
        this.table.getColumns().add(getColumn(id, name));
        return this;
    }

    public TableBuilder createIdColumn (Long id) {
        DataColumn column = getColumn(id, "id");
        column.setPrimaryKey(true);
        this.table.getColumns().add(column);
        return this;
    }

    private DataColumn getColumn(Long id, String name) {
        DataColumn column = spy(DataColumn.class);
        when(column.getId()).thenReturn(id);
        column.setColumnName(name);
        return column;
    }

    public DataTable build() {
        return this.table;
    }
}
