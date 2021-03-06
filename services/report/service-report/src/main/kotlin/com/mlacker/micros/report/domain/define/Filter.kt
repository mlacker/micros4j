package com.mlacker.micros.report.domain.define

import com.mlacker.micros.report.domain.datasource.Expression

class Filter(
    val id: Long,
    var name: String,
    var expression: Expression,
    var dataType: String = "text",
    var compareType: String = "like",
    var placeholder: String
)
