package com.mlacker.micros.data.domain.statement;

import net.sf.jsqlparser.expression.JdbcParameter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ParameterStatementTest {

    @Test
    public void addOneParameter() {
        ParameterStatement statement = new ParameterStatement();
        JdbcParameter jdbcParameter = statement.addParameter(1);

        assertEquals(1, statement.getParameters().length);
        assertEquals("?", jdbcParameter.toString());
    }

    @Test
    public void addThreeParameter() {
        ParameterStatement statement = new ParameterStatement();
        statement.addParameter(1);
        statement.addParameter(2);
        JdbcParameter jdbcParameter = statement.addParameter(3);

        assertEquals(3, statement.getParameters().length);
        assertEquals("?", jdbcParameter.toString());
    }
}