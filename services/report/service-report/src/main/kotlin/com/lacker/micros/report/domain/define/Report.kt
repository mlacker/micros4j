package com.lacker.micros.report.domain.define

import com.lacker.micros.report.domain.datasource.*

class Report(
        override val id: Long,
        var name: String,
        val table: SourceTable,
        val createdBy: String,
        val createdDate: String
) : SourceTable {
    val joins: List<Join> = listOf()
    val columns: List<ReportColumn> = listOf()
    val filters: List<Filter> = listOf()
    val groupBies: List<DataColumnOfAlias> = listOf()
    val orderBies: List<OrderBy> = listOf()

    var lastModifiedBy: String? = null
    var lastModifiedDate: String? = null

    override val primaryKeyId get() = "id"
    val groupByEnabled get() = this.groupBies.isNotEmpty()
}