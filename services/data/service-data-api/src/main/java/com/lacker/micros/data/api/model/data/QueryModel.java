package com.lacker.micros.data.api.model.data;

import java.util.Map;

public class QueryModel {

    private String statement;
    private Map<String, Object> params;

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
}
