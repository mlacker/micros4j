package com.lacker.micros.data.api.model.form.load;

import java.util.List;

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
