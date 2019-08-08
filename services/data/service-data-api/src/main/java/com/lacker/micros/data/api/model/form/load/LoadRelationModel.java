package com.lacker.micros.data.api.model.form.load;

public class LoadRelationModel {
    private String id;
    private String primaryColumn;
    private LoadTableModel foreignTable;
    private String foreignColumn;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrimaryColumn() {
        return primaryColumn;
    }

    public void setPrimaryColumn(String primaryColumn) {
        this.primaryColumn = primaryColumn;
    }

    public LoadTableModel getForeignTable() {
        return foreignTable;
    }

    public void setForeignTable(LoadTableModel foreignTable) {
        this.foreignTable = foreignTable;
    }

    public String getForeignColumn() {
        return foreignColumn;
    }

    public void setForeignColumn(String foreignColumn) {
        this.foreignColumn = foreignColumn;
    }
}
