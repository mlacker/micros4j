package com.mlacker.micros.data.api.model.data;

import java.util.ArrayList;
import java.util.List;

public class QueryModel {

    private String statement;
    private List<Object> parameters = new ArrayList<>();

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public List<Object> getParameters() {
        return parameters;
    }

    public void setParameters(List<Object> parameters) {
        this.parameters = parameters;
    }
}
