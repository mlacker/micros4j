package com.mlacker.micros.report.domain.datasource

import net.sf.jsqlparser.expression.Alias
import net.sf.jsqlparser.schema.Table

interface SourceTable {
    val id: Long
    val primaryKeyId: String

    fun buildTable(alias: String): Table =
            Table("`$id`").apply { this.alias = Alias("`$alias`") }
}