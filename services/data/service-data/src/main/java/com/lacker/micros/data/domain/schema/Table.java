package com.lacker.micros.data.domain.schema;

import com.lacker.micros.domain.entity.AggregateRoot;
import com.lacker.micros.domain.entity.EntityImpl;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.FromItemVisitor;
import net.sf.jsqlparser.statement.select.Pivot;

import java.util.List;

public class Table extends EntityImpl implements AggregateRoot, FromItem {

    private String name;
    private String tableName;
    private List<Column> columns;
    private boolean isProtected;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public boolean isProtected() {
        return isProtected;
    }

    public void setProtected(boolean aProtected) {
        isProtected = aProtected;
    }

    @Override
    public void accept(FromItemVisitor fromItemVisitor) {
    }

    @Override
    public Alias getAlias() {
        return null;
    }

    @Override
    public void setAlias(Alias alias) {

    }

    @Override
    public Pivot getPivot() {
        return null;
    }

    @Override
    public void setPivot(Pivot pivot) {
    }

    @Override
    public String toString() {
        return getTableName()
                + ((getAlias() != null) ? getAlias().toString() : "");
    }
}
