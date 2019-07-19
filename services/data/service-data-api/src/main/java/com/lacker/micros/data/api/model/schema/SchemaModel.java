package com.lacker.micros.data.api.model.schema;

import java.util.List;

public class SchemaModel {

    private String primary;
    private List<TableModel> tables;
    private List<RelationModel> relations;

    public String getPrimary() {
        return primary;
    }

    public void setPrimary(String primary) {
        this.primary = primary;
    }

    public List<TableModel> getTables() {
        return tables;
    }

    public void setTables(List<TableModel> tables) {
        this.tables = tables;
    }

    public List<RelationModel> getRelations() {
        return relations;
    }

    public void setRelations(List<RelationModel> relations) {
        this.relations = relations;
    }
}
