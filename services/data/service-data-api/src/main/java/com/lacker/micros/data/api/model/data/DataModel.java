package com.lacker.micros.data.api.model.data;

import java.util.List;
import java.util.Map;

public class DataModel {

    private String tableId;
    private List<String> includeColumns;
    private Map<String, List<String>> conditions;
    private List<Map<String, Object>> dataMaps;

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public List<String> getIncludeColumns() {
        return includeColumns;
    }

    public void setIncludeColumns(List<String> includeColumns) {
        this.includeColumns = includeColumns;
    }

    public Map<String, List<String>> getConditions() {
        return conditions;
    }

    public void setConditions(Map<String, List<String>> conditions) {
        this.conditions = conditions;
    }

    public List<Map<String, Object>> getDataMaps() {
        return dataMaps;
    }

    public void setDataMaps(List<Map<String, Object>> dataMaps) {
        this.dataMaps = dataMaps;
    }
}
