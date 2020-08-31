package com.mlacker.micros.report.domain.datasource

interface SourceColumn {

    val id: Long
    val name: String
    val expression: Expression
}