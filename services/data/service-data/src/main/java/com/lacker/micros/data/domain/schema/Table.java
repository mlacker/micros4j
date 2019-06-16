package com.lacker.micros.data.domain.schema;

import com.lacker.micros.domain.entity.AggregateRoot;
import com.lacker.micros.domain.entity.EntityImpl;

import java.util.List;

public class Table extends EntityImpl implements AggregateRoot {

    private String name;
    private String tableName;
    private List<Column> columns;
    private boolean isProtected;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public boolean isProtected() {
        return isProtected;
    }

    public void setProtected(boolean aProtected) {
        isProtected = aProtected;
    }
}