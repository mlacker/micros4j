package com.lacker.micros.data.service.statement.builder;

import com.lacker.micros.data.domain.schema.DataColumn;
import com.lacker.micros.data.domain.schema.DataTable;
import com.lacker.micros.data.domain.statement.ParameterStatement;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.MultiExpressionList;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.update.Update;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FormStatementBuilder {

    // SELECT id, a, b FROM table WHERE id = ? AND deleted = 0
    // SELECT id, a, b FROM table WHERE fk = ? AND deleted = 0
    public ParameterStatement select(DataTable table, List<String> includeColumns, DataColumn conditionColumn, Object value) {
        ParameterStatement statement = new ParameterStatement();

        List<SelectItem> selectItems = table.getColumns().stream()
                .filter(m -> includeColumns.contains(m.getId()))
                .map(m -> {
                    SelectExpressionItem selectExpressionItem = new SelectExpressionItem();
                    selectExpressionItem.setExpression(getColumn(m));
                    selectExpressionItem.setAlias(new Alias(m.getId()));
                    return (SelectItem) selectExpressionItem;
                })
                .collect(Collectors.toList());

        EqualsTo equalsTo = new EqualsTo();
        equalsTo.setLeftExpression(getColumn(conditionColumn));
        equalsTo.setRightExpression(statement.addParameter(value));

        EqualsTo notDeleted = new EqualsTo();
        notDeleted.setRightExpression(getColumn(table.getDeleteFlag()));
        notDeleted.setRightExpression(new LongValue(0));

        Expression where = new AndExpression(equalsTo, notDeleted);

        PlainSelect plainSelect = new PlainSelect();
        plainSelect.setSelectItems(selectItems);
        plainSelect.setFromItem(getTable(table));
        plainSelect.setWhere(where);

        Select select = new Select();
        select.setSelectBody(plainSelect);

        statement.setStatement(select);

        return statement;
    }

    // SELECT id FROM table WHERE (id IN (?, ?, ?) OR fk1 IN (?, ?, ?) OR fk2 IN (?, ?, ?)) AND deleted = 0
    public ParameterStatement selectIn(DataTable table, Map<String, List<String>> conditions) {
        ParameterStatement statement = new ParameterStatement();

        SelectExpressionItem selectExpressionItem = new SelectExpressionItem();
        selectExpressionItem.setExpression(getColumn(table.getPrimaryKey()));
        selectExpressionItem.setAlias(new Alias(table.getPrimaryKey().getId()));
        List<SelectItem> selectItems = Collections.singletonList(selectExpressionItem);

        Expression leftExpression = null;
        for (Map.Entry<String, List<String>> entry : conditions.entrySet()) {
            Column column = getColumn(table.getColumn(entry.getKey()));
            ExpressionList expressionList = new ExpressionList(entry.getValue().stream().map(statement::addParameter).collect(Collectors.toList()));
            InExpression inExpression = new InExpression(column, expressionList);

            if (leftExpression == null) {
                leftExpression = inExpression;
            } else {
                leftExpression = new OrExpression(leftExpression, inExpression);
            }
        }

        EqualsTo notDeleted = new EqualsTo();
        notDeleted.setRightExpression(getColumn(table.getDeleteFlag()));
        notDeleted.setRightExpression(new LongValue(0));

        Expression where = new AndExpression(new Parenthesis(leftExpression), notDeleted);

        PlainSelect plainSelect = new PlainSelect();
        plainSelect.setSelectItems(selectItems);
        plainSelect.setFromItem(getTable(table));
        plainSelect.setWhere(where);

        Select select = new Select();
        select.setSelectBody(plainSelect);

        statement.setStatement(select);

        return statement;
    }

    // INSERT INTO table (id, a, b) VALUES (?, ?, ?), (?, ?, ?)
    public ParameterStatement insert(DataTable table, List<String> includeColumns, List<Map<String, Object>> rows) {
        List<DataColumn> columns = table.getColumns().stream()
                .filter(m -> includeColumns.contains(m.getId()))
                .collect(Collectors.toList());

        ParameterStatement statement = new ParameterStatement();
        MultiExpressionList multiExpressionList = new MultiExpressionList();
        for (Map<String, Object> row : rows) {
            List<Expression> expressions = new ArrayList<>();
            for (DataColumn column : columns) {
                Object value = row.get(column.getId());

                if (value != null) {
                    expressions.add(statement.addParameter(value));
                }
            }
            multiExpressionList.addExpressionList(expressions);
        }

        Insert insert = new Insert();
        insert.setTable(getTable(table));
        insert.setColumns(columns.stream().map(this::getColumn)
                .collect(Collectors.toList()));
        insert.setItemsList(multiExpressionList);
        statement.setStatement(insert);

        return statement;
    }

    // UPDATE table SET a = ?, b = ? WHERE id = ?
    public List<ParameterStatement> update(DataTable table, List<String> includeColumns, List<Map<String, Object>> rows) {
        List<ParameterStatement> statements = new ArrayList<>();
        List<DataColumn> columns = table.getColumns().stream()
                .filter(m -> !m.equals(table.getPrimaryKey()) && includeColumns.contains(m.getId()))
                .collect(Collectors.toList());
        List<Table> updateTable = Collections.singletonList(getTable(table));
        Column updatePrimaryColumn = getColumn(table.getPrimaryKey());
        List<Column> updateColumns = columns.stream().map(this::getColumn).collect(Collectors.toList());

        for (Map<String, Object> row : rows) {
            ParameterStatement statement = new ParameterStatement();

            List<Expression> expressions = new ArrayList<>();
            for (DataColumn column : columns) {
                expressions.add(statement.addParameter(row.get(column.getId())));
            }

            EqualsTo equalsTo = new EqualsTo();
            equalsTo.setLeftExpression(updatePrimaryColumn);
            equalsTo.setRightExpression(statement.addParameter(row.get(table.getPrimaryKey().getId())));

            Update update = new Update();
            update.setTables(updateTable);
            update.setColumns(updateColumns);
            update.setExpressions(expressions);
            update.setWhere(equalsTo);

            statements.add(statement);
        }

        return statements;
    }

    // UPDATE table SET deleted = 1 WHERE id IN (?, ?, ?)
    public ParameterStatement delete(DataTable table, List<String> rows) {
        ParameterStatement statement = new ParameterStatement();

        Column idColumn = getColumn(table.getPrimaryKey());
        ExpressionList idParameters = new ExpressionList(
                rows.stream().map(statement::addParameter).collect(Collectors.toList()));
        InExpression inExpression = new InExpression(idColumn, idParameters);

        Delete delete = new Delete();
        delete.setTable(getTable(table));
        delete.setWhere(inExpression);

        return statement;
    }

    // UPDATE table SET deleted = 1 WHERE id IN (?)
    public ParameterStatement delete(DataTable table, String id) {
        return delete(table, Collections.singletonList(id));
    }

    private Table getTable(DataTable table) {
        return new Table(table.getTableName());
    }

    private Column getColumn(DataColumn column) {
        return new Column(column.getColumnName());
    }
}
