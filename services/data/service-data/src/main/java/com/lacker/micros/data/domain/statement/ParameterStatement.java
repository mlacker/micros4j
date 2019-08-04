package com.lacker.micros.data.domain.statement;

import net.sf.jsqlparser.expression.JdbcParameter;
import net.sf.jsqlparser.statement.Statement;

import java.util.ArrayList;
import java.util.List;

public class ParameterStatement {
    private Statement statement;
    private List<Object> parameters = new ArrayList<>();

    public Statement getStatement() {
        return statement;
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
    }

    public List<Object> getParameters() {
        return parameters;
    }

    public JdbcParameter addParameter(Object value) {
        parameters.add(value);

        return new JdbcParameter(parameters.size() - 1, true);
    }
}
