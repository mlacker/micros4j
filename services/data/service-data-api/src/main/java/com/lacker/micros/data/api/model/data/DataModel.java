package com.lacker.micros.data.api.model.data;

import java.util.List;
import java.util.Map;

public class DataModel {

    private Long tableId;
    private List<Long> includeColumns;
    private Map<Long, List<Object>> conditions;
    private List<Map<Long, Object>> dataMaps;

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public List<Long> getIncludeColumns() {
        return includeColumns;
    }

    public void setIncludeColumns(List<Long> includeColumns) {
        this.includeColumns = includeColumns;
    }

    public Map<Long, List<Object>> getConditions() {
        return conditions;
    }

    public void setConditions(Map<Long, List<Object>> conditions) {
        this.conditions = conditions;
    }

    public List<Map<Long, Object>> getDataMaps() {
        return dataMaps;
    }

    public void setDataMaps(List<Map<Long, Object>> dataMaps) {
        this.dataMaps = dataMaps;
    }
}
