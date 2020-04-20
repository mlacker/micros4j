package com.lacker.micros.data.api.model.form.load;

import java.util.List;

public class LoadTableModel {
    private Long id;
    private List<Long> includeColumns;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getIncludeColumns() {
        return includeColumns;
    }

    public void setIncludeColumns(List<Long> includeColumns) {
        this.includeColumns = includeColumns;
    }
}
