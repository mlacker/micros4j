package com.mlacker.micros.report.domain.define

class OrderBy (
    val id: Long,
    var column: ReportColumn,
    var orderType: String = "ASC"
)
