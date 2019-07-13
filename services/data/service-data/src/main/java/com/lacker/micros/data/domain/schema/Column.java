package com.lacker.micros.data.domain.schema;

import com.lacker.micros.domain.entity.EntityImpl;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.ExpressionVisitor;
import net.sf.jsqlparser.parser.SimpleNode;

public class Column extends EntityImpl implements Expression {

    private String name;
    private String tableId;
    private String columnName;
    private ColumnType columnType;
    private boolean primaryKey = false;
    private boolean isProtected = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public ColumnType getColumnType() {
        return columnType;
    }

    public void setColumnType(ColumnType columnType) {
        this.columnType = columnType;
    }

    public boolean isPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(boolean primaryKey) {
        this.primaryKey = primaryKey;
    }

    public boolean isProtected() {
        return isProtected;
    }

    public void setProtected(boolean aProtected) {
        isProtected = aProtected;
    }

    @Override
    public void accept(ExpressionVisitor expressionVisitor) {
    }

    private SimpleNode node;

    @Override
    public SimpleNode getASTNode() {
        return node;
    }

    @Override
    public void setASTNode(SimpleNode node) {
        this.node = node;
    }

    public Table getTable() {
        return null;
    }

    public String getColumnName(boolean aliases) {
        StringBuilder fqn = new StringBuilder();

        if (getTable() != null) {
            if (getTable().getAlias() != null && aliases) {
                fqn.append(getTable().getAlias().getName());
            } else {
                fqn.append(getTable().toString());
            }
        }
        if (fqn.length() > 0) {
            fqn.append('.');
        }
        if (columnName != null) {
            fqn.append(columnName);
        }
        return fqn.toString();
    }

    @Override
    public String toString() {
        return getColumnName(true);
    }
}
