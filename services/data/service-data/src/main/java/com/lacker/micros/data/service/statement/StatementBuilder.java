package com.lacker.micros.data.service.statement;

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
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.update.Update;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class StatementBuilder {

    public ParameterStatement select(DataTable table, List<String> includeColumns, DataColumn conditionColumn, Object value) {
        ParameterStatement statement = new ParameterStatement();

        List<SelectItem> selectItems = table.getColumns().stream()
                .filter(m -> includeColumns.contains(m.getId()))
                .map(m -> {
                    SelectExpressionItem selectExpressionItem = new SelectExpressionItem();
                    selectExpressionItem.setExpression(getColumn(m));
                    selectExpressionItem.setAlias(new Alias(appendBackQuote(m.getId())));
                    return (SelectItem) selectExpressionItem;
                })
                .collect(Collectors.toList());

        EqualsTo equalsTo = new EqualsTo();
        equalsTo.setLeftExpression(getColumn(conditionColumn));
        equalsTo.setRightExpression(statement.addParameter(value));

        Expression where = withNotDeleted(table, equalsTo);
        statement.setStatement(buildSelect(selectItems, table, where));

        return statement;
    }

    public ParameterStatement selectIn(DataTable table, Map<String, List<String>> conditions) {
        ParameterStatement statement = new ParameterStatement();

        SelectExpressionItem selectExpressionItem = new SelectExpressionItem();
        selectExpressionItem.setExpression(getColumn(table.getPrimaryKey()));
        selectExpressionItem.setAlias(new Alias(appendBackQuote(table.getPrimaryKey().getId())));
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

        Expression where = withNotDeleted(table, leftExpression);
        statement.setStatement(buildSelect(selectItems, table, where));

        return statement;
    }

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

    public List<ParameterStatement> update(DataTable table, List<String> includeColumns, List<Map<String, Object>> rows) {
        List<ParameterStatement> statements = new ArrayList<>();
        List<DataColumn> columns = table.getColumns().stream()
                .filter(m -> !m.equals(table.getPrimaryKey()) && includeColumns.contains(m.getId()))
                .collect(Collectors.toList());
        Column updatePrimaryColumn = getColumn(table.getPrimaryKey());
        List<Column> updateColumns = columns.stream().map(this::getColumn).collect(Collectors.toList());

        for (Map<String, Object> row : rows) {
            ParameterStatement statement = new ParameterStatement();

            EqualsTo equalsTo = new EqualsTo();
            equalsTo.setLeftExpression(updatePrimaryColumn);
            equalsTo.setRightExpression(statement.addParameter(row.get(table.getPrimaryKey().getId())));

            List<Expression> expressions = new ArrayList<>();
            for (DataColumn column : columns) {
                expressions.add(statement.addParameter(row.get(column.getId())));
            }

            Update update = new Update();
            update.setTable(getTable(table));
            update.setColumns(updateColumns);
            update.setExpressions(expressions);
            update.setWhere(equalsTo);

            statement.setStatement(update);
            statements.add(statement);
        }

        return statements;
    }

    public ParameterStatement delete(DataTable table, List<String> rows) {
        ParameterStatement statement = new ParameterStatement();

        Column idColumn = getColumn(table.getPrimaryKey());
        ExpressionList idParameters = new ExpressionList(
                rows.stream().map(statement::addParameter).collect(Collectors.toList()));
        InExpression inExpression = new InExpression(idColumn, idParameters);

        Update update = new Update();
        update.setTable(getTable(table));
        update.setColumns(Collections.singletonList(getColumn(table.getDeleteFlag())));
        update.setExpressions(Collections.singletonList(new LongValue(1)));
        update.setWhere(inExpression);

        statement.setStatement(update);
        return statement;
    }

    public ParameterStatement delete(DataTable table, String id) {
        return delete(table, Collections.singletonList(id));
    }

    private Table getTable(DataTable table) {
        return new Table(appendBackQuote(table.getTableName()));
    }

    private Column getColumn(DataColumn column) {
        return new Column(appendBackQuote(column.getColumnName()));
    }

    private String appendBackQuote(String text) {
        return "`" + text + "`";
    }

    private Select buildSelect(List<SelectItem> selectItems, DataTable table, Expression where) {
        PlainSelect plainSelect = new PlainSelect();
        plainSelect.setSelectItems(selectItems);
        plainSelect.setFromItem(getTable(table));
        plainSelect.setWhere(where);

        Select select = new Select();
        select.setSelectBody(plainSelect);
        return select;
    }

    private Expression withNotDeleted(DataTable table, Expression expression) {
        EqualsTo notDeleted = new EqualsTo();
        notDeleted.setLeftExpression(getColumn(table.getDeleteFlag()));
        notDeleted.setRightExpression(new LongValue(0));

        return new AndExpression(new Parenthesis(expression), notDeleted);
    }
}
