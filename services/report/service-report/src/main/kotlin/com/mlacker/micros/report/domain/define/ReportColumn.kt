package com.mlacker.micros.report.domain.define

import com.mlacker.micros.report.domain.datasource.Expression

class ReportColumn (
    val id: Long,
    var name: String,
    var expression: Expression
) {
    var format: String? = null
    var sort: Int = 0
}
