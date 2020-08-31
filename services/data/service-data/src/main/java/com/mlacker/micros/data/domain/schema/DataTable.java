package com.mlacker.micros.data.domain.schema;

import com.mlacker.micros.domain.entity.AggregateRoot;
import com.mlacker.micros.domain.entity.EntityImpl;
import java.util.ArrayList;
import java.util.List;

public class DataTable extends EntityImpl implements AggregateRoot {

    private String name;
    private String tableName;
    private List<DataColumn> columns = new ArrayList<>();
    private boolean isProtected;

    public DataTable(long id) {
        super(id);
    }

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

    public List<DataColumn> getColumns() {
        return columns;
    }

    public void setColumns(List<DataColumn> columns) {
        this.columns = columns;
    }

    public boolean isProtected() {
        return isProtected;
    }

    public void setProtected(boolean aProtected) {
        isProtected = aProtected;
    }

    public DataColumn getColumn(Long columnId) {
        return this.columns.stream().filter(m -> columnId.equals(m.getId())).findAny().orElse(null);
    }

    private DataColumn getColumnByName(String columnName) {
        return columns.stream().filter(m -> m.getColumnName().equals(columnName)).findAny().orElse(null);
    }

    private DataColumn primaryKey;
    public DataColumn getPrimaryKey() {
        if (primaryKey == null) {
            primaryKey = columns.stream()
                    .filter(DataColumn::isPrimaryKey).findAny()
                    .orElseThrow(() -> new IllegalStateException("Column primary key is missing, table: " + getId()));
        }

        return primaryKey;
    }

    public DataColumn getDeleteFlag() {
        DataColumn deleted = getColumnByName("deleted");

        if (deleted == null) {
            throw new IllegalStateException("Column deleted is missing, table: " + getId());
        }

        return deleted;
    }
}
