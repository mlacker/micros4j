package com.lacker.micros.data.service;

import com.lacker.micros.data.domain.schema.Column;
import com.lacker.micros.data.domain.schema.Table;
import com.lacker.micros.data.domain.schema.TableRepository;
import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.CaseExpression;
import net.sf.jsqlparser.expression.CastExpression;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.ExpressionVisitor;
import net.sf.jsqlparser.expression.NotExpression;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.SignedExpression;
import net.sf.jsqlparser.expression.WhenClause;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.IsNullExpression;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.FromItemVisitor;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItemVisitor;
import net.sf.jsqlparser.statement.select.SelectVisitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SchemaTransformer extends StatementVisitorAdapter implements SelectVisitor, SelectItemVisitor, FromItemVisitor, ExpressionVisitor {

    private final TableRepository tableRepo;
    private final List<Table> tables = new ArrayList<>();

    public SchemaTransformer(TableRepository tableRepo) {
        this.tableRepo = tableRepo;
    }

    @Override
    protected boolean allowColumnProcessing() {
        return false;
    }

    @Override
    public void visit(PlainSelect plainSelect) {
        super.visit(plainSelect);

        getTable(plainSelect.getFromItem()).ifPresent(plainSelect::setFromItem);

        if (plainSelect.getJoins() != null) {
            for (Join join : plainSelect.getJoins()) {
                getTable(join.getRightItem()).ifPresent(join::setRightItem);
            }
        }
    }

    @Override
    public void visit(SelectExpressionItem item) {
        getColumn(item.getExpression()).ifPresent(item::setExpression);
    }

    @Override
    public void visit(InExpression inExpression) {
        super.visit(inExpression);
        getColumn(inExpression.getLeftExpression()).ifPresent(inExpression::setLeftExpression);
    }

    @Override
    public void visit(SignedExpression signedExpression) {
        super.visit(signedExpression);
        getColumn(signedExpression.getExpression()).ifPresent(signedExpression::setExpression);
    }

    @Override
    public void visit(IsNullExpression isNullExpression) {
    }

    @Override
    public void visit(Parenthesis parenthesis) {
    }

    @Override
    public void visit(NotExpression notExpr) {
    }

    @Override
    public void visit(ExpressionList expressionList) {
        for (Expression expression : expressionList.getExpressions()) {
        }
    }

    @Override
    public void visit(CaseExpression caseExpression) {
    }

    @Override
    public void visit(WhenClause whenClause) {
    }

    @Override
    public void visit(CastExpression cast) {
    }

    @Override
    protected void visitBinaryExpression(BinaryExpression binaryExpression) {
        super.visitBinaryExpression(binaryExpression);
        getColumn(binaryExpression.getLeftExpression()).ifPresent(binaryExpression::setLeftExpression);
        getColumn(binaryExpression.getRightExpression()).ifPresent(binaryExpression::setRightExpression);
    }

    private Optional<Table> getTable(FromItem fromItem) {
        if (fromItem instanceof net.sf.jsqlparser.schema.Table) {
            net.sf.jsqlparser.schema.Table tableName = (net.sf.jsqlparser.schema.Table) fromItem;
            return getTable(tableName.getName());
        }
        return Optional.empty();
    }

    private Optional<Table> getTable(String key) {
        Optional<Table> tableOptional = tables.stream()
                .filter(m -> m.getId().equals(key)).findAny();

        if (!tableOptional.isPresent()) {
            tableOptional = tableRepo.find(key);

            tableOptional.ifPresent(tables::add);
        }

        return tableOptional;
    }

    private Optional<Column> getColumn (Expression expression) {
        if (expression instanceof net.sf.jsqlparser.schema.Column) {
            net.sf.jsqlparser.schema.Column tableColumn = (net.sf.jsqlparser.schema.Column) expression;

            Optional<Table> tableOptional = getTable(tableColumn.getTable().getName());
            if (tableOptional.isPresent()) {
                return tableOptional.get().getColumns().stream()
                        .filter(m -> m.getId().equals(tableColumn.getName(false))).findAny();
            }
        }

        return Optional.empty();
    }
}
