package com.lacker.micros.data.service;

import com.lacker.micros.data.domain.schema.DataColumn;
import com.lacker.micros.data.domain.schema.DataTable;

public class TableBuilder {

    private final DataTable table;

    private TableBuilder(DataTable table) {
        this.table = table;
    }

    public static TableBuilder createTable (String id, String name) {
        DataTable table = new DataTable();
        table.setId(id);
        table.setTableName(name);
        return new TableBuilder(table);
    }

    public TableBuilder createColumn (String id, String name) {
        this.table.getColumns().add(getColumn(id, name));
        return this;
    }

    public TableBuilder createIdColumn (String id) {
        DataColumn column = getColumn(id, "id");
        column.setPrimaryKey(true);
        this.table.getColumns().add(column);
        return this;
    }

    private DataColumn getColumn(String id, String name) {
        DataColumn column = new DataColumn();
        column.setId(id);
        column.setColumnName(name);
        return column;
    }

    public DataTable build() {
        return this.table;
    }
}
