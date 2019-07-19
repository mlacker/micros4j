package com.lacker.micros.data.service.statement.builder;

import net.sf.jsqlparser.expression.JdbcParameter;
import net.sf.jsqlparser.statement.Statement;

import java.util.ArrayList;
import java.util.List;

public abstract class StatementBuilder {

    private final List<Object> params;

    public StatementBuilder() {
        params = new ArrayList<>();
    }

    public List<Object> getParams() {
        return params;
    }

    public abstract Statement build();

    protected JdbcParameter addParameter(Object value) {
        params.add(value);
        return new JdbcParameter(params.size(), true);
    }
}
