package com.mlacker.micros.report.domain.datasource

class DataTable(
        override val id: Long,
        override val primaryKeyId: String
) : SourceTable {

    val name: String? = null
    val columns: List<DataColumn>? = null
}