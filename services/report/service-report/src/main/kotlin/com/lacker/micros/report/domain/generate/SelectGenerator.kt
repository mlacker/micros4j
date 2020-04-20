package com.lacker.micros.report.domain.generate

import com.lacker.micros.report.domain.datasource.DataColumnOfAlias
import com.lacker.micros.report.domain.define.*
import com.lacker.micros.report.domain.define.Join
import net.sf.jsqlparser.expression.*
import net.sf.jsqlparser.expression.Function
import net.sf.jsqlparser.expression.operators.conditional.AndExpression
import net.sf.jsqlparser.expression.operators.relational.EqualsTo
import net.sf.jsqlparser.schema.Column
import net.sf.jsqlparser.statement.select.*

class SelectGenerator {

    fun buildSelect(report: Report): Select {
        val select = Select()

        if (report.table is Report) {
            val withItem = WithItem()
            withItem.name = "`${report.table.id}`"
            withItem.selectBody = buildPlainSelect(report.table)

            select.withItemsList = listOf(withItem)
        }

        val plainSelect = buildPlainSelect(report)
        plainSelect.limit = Limit()
                .apply { offset = LongValue(0) }
                .apply { rowCount = LongValue(10) }
        select.selectBody = plainSelect

        return select
    }

    fun buildSelectCount(report: Report): Select {
        val selectItems = listOf(SelectExpressionItem(Function().apply { name = "COUNT" }.apply { isAllColumns = true }))
        val plainSelect = buildPlainSelect(report)
        plainSelect.orderByElements = null

        val select = Select()

        if (!report.groupByEnabled) {
            plainSelect.selectItems = selectItems
            select.selectBody = plainSelect
        } else {
            plainSelect.selectItems = listOf(SelectExpressionItem(LongValue(1)))
            select.selectBody = PlainSelect().apply {
                this.selectItems = selectItems
                this.fromItem = SubSelect().apply { selectBody = plainSelect }
            }
        }
        return select
    }

    private fun buildPlainSelect(report: Report): PlainSelect {
        return PlainSelect().apply {
            selectItems = buildColumns(DataColumnOfAlias(report.id, report.table.primaryKeyId), report.columns)
            fromItem = report.table.buildTable(report.id.toString())
            joins = buildJoins(report.joins)
            where = buildWhere(report.filters)
            if (report.groupByEnabled) {
                report.groupBies.map { it.sql() }.forEach(this::addGroupByColumnReference)
            }
            orderByElements =
                    if (report.orderBies.isNotEmpty())
                        buildOrderBies(report.orderBies)
                    else
                        listOf(OrderByElement().apply {
                            expression = Column("`id`")
                            isAsc = false
                        })
        }
    }

    private fun buildColumns(
            idColumn: com.lacker.micros.report.domain.datasource.Expression,
            columns: List<ReportColumn>): List<SelectItem> {
        return mutableListOf<SelectItem>()
                .apply { add(SelectExpressionItem(idColumn.sql()).apply { alias = Alias("`id`") }) }
                .apply { addAll(columns.map { SelectExpressionItem(it.expression.sql()).apply { alias = Alias("`${it.id}`") } }) }
    }

    private fun buildJoins(joins: List<Join>): List<net.sf.jsqlparser.statement.select.Join> {
        return joins.map {
            net.sf.jsqlparser.statement.select.Join().apply {
                isLeft = true
                rightItem = it.foreign.buildTable(it.id.toString())
                onExpression = EqualsTo().apply {
                    leftExpression = it.primaryColumn.sql()
                    rightExpression = it.foreignColumn.sql()
                }
            }
        }
    }

    private fun buildWhere(filters: List<Filter>): Expression? {
        var where: Expression? = null
        for (filter in filters) {
            val expression = EqualsTo()
                    .apply { leftExpression = filter.expression.sql() }
                    .apply { rightExpression = JdbcParameter() }

            where = where?.let { AndExpression(it, expression) } ?: expression
        }

        return where
    }

    private fun buildOrderBies(orderBies: List<OrderBy>): List<OrderByElement> {
        return orderBies.map {
            OrderByElement().apply {
                expression = Column("`${it.column.id}`")
                isAsc = it.orderType == "ASC"
                isAscDescPresent = true
            }
        }
    }
}
