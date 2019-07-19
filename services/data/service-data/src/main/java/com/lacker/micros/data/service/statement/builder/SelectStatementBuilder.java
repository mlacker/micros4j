package com.lacker.micros.data.service.statement.builder;

import com.lacker.micros.data.domain.schema.Column;
import com.lacker.micros.data.domain.schema.Table;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;

import java.util.List;
import java.util.stream.Collectors;

public class SelectStatementBuilder extends StatementBuilder {

    @Override
    public Statement build() {
        return null;
    }

    private Select buildQueryStatement(Table table, List<String> includeColumns, Column conditionColumn, Object value) {
        List<SelectItem> selectItems = table.getColumns().stream()
                .filter(m -> includeColumns.contains(m.getId()))
                .map(m -> {
                    SelectExpressionItem selectExpressionItem = new SelectExpressionItem();
                    selectExpressionItem.setExpression(new net.sf.jsqlparser.schema.Column(m.getColumnName()));
                    selectExpressionItem.setAlias(new Alias(m.getId()));
                    return (SelectItem) selectExpressionItem;
                })
                .collect(Collectors.toList());

        FromItem fromItem = new net.sf.jsqlparser.schema.Table(table.getTableName());

        EqualsTo equalsTo = new EqualsTo();
        equalsTo.setLeftExpression(new net.sf.jsqlparser.schema.Column(conditionColumn.getColumnName()));
        equalsTo.setRightExpression(addParameter(value));

        EqualsTo stateEqualsTo = new EqualsTo();
        stateEqualsTo.setRightExpression(new net.sf.jsqlparser.schema.Column(table.getDeleteFlag().getColumnName()));
        stateEqualsTo.setRightExpression(new LongValue(0));

        Expression where = new AndExpression(equalsTo, stateEqualsTo);

        PlainSelect plainSelect = new PlainSelect();
        plainSelect.setSelectItems(selectItems);
        plainSelect.setFromItem(fromItem);
        plainSelect.setWhere(where);

        Select select = new Select();
        select.setSelectBody(plainSelect);

        return select;
    }
}
