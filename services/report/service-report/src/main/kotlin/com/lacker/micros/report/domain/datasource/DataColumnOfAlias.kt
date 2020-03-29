package com.lacker.micros.report.domain.datasource

import net.sf.jsqlparser.schema.Column
import net.sf.jsqlparser.schema.Table
import kotlin.String

class DataColumnOfAlias private constructor(
        private val alias: String, private val column: String
) : Expression {
    constructor(alias: Long, column: String) : this(alias.toString(), column)
    constructor(text: String) : this(text.split('.')[0], text.split('.')[1])

    override fun sql(): net.sf.jsqlparser.expression.Expression {
        // FIXME Table is not real entry
        return Column(Table("`$alias`"), "`$column`")
    }
}