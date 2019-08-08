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
}
