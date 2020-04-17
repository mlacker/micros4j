package com.lacker.micros.data.api.model.form.load;

public class LoadRelationModel {
    private Long id;
    private Long primaryColumn;
    private LoadTableModel foreignTable;
    private Long foreignColumn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPrimaryColumn() {
        return primaryColumn;
    }

    public void setPrimaryColumn(Long primaryColumn) {
        this.primaryColumn = primaryColumn;
    }

    public LoadTableModel getForeignTable() {
        return foreignTable;
    }

    public void setForeignTable(LoadTableModel foreignTable) {
        this.foreignTable = foreignTable;
    }

    public Long getForeignColumn() {
        return foreignColumn;
    }

    public void setForeignColumn(Long foreignColumn) {
        this.foreignColumn = foreignColumn;
    }
}
