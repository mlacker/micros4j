package com.lacker.micros.data.api.model.form.load;

import java.util.List;

public class LoadSchemaModel {

    private LoadTableModel primary;
    private List<LoadRelationModel> relations;

    public LoadTableModel getPrimary() {
        return primary;
    }

    public void setPrimary(LoadTableModel primary) {
        this.primary = primary;
    }

    public List<LoadRelationModel> getRelations() {
        return relations;
    }

    public void setRelations(List<LoadRelationModel> relations) {
        this.relations = relations;
    }

    public class LoadTableModel {
        private String id;
        private List<String> includeColumns;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<String> getIncludeColumns() {
            return includeColumns;
        }

        public void setIncludeColumns(List<String> includeColumns) {
            this.includeColumns = includeColumns;
        }
    }

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
}
