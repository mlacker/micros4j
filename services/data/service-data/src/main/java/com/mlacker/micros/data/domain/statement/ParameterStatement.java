package com.mlacker.micros.data.domain.statement;

import java.util.ArrayList;
import java.util.List;
import net.sf.jsqlparser.expression.JdbcParameter;
import net.sf.jsqlparser.statement.Statement;

public class ParameterStatement {
    private Statement statement;
    private List<Object> parameters = new ArrayList<>();

    public ParameterStatement() {
    }

    public ParameterStatement(Statement statement, List<Object> parameters) {
        this.statement = statement;
        this.parameters = parameters;
    }

    public Statement getStatement() {
        return statement;
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
    }

    public Object[] getParameters() {
        return parameters.toArray();
    }

    public JdbcParameter addParameter(Object value) {
        parameters.add(value);

        return new JdbcParameter(parameters.size() - 1, false);
    }

    @Override
    public String toString() {
        return statement.toString();
    }
}
