package com.lacker.micros.data.api.model.data;

import java.util.List;
import java.util.Map;

public class DataModel {

    private String tableId;
    private List<Map<String, Object>> dataMaps;

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public List<Map<String, Object>> getDataMaps() {
        return dataMaps;
    }

    public void setDataMaps(List<Map<String, Object>> dataMaps) {
        this.dataMaps = dataMaps;
    }
}
