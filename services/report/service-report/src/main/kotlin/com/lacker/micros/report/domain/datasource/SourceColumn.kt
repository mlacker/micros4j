package com.lacker.micros.report.domain.datasource

import kotlin.String

interface SourceColumn {

    val id: Long
    val name: String
    val expression: Expression
}