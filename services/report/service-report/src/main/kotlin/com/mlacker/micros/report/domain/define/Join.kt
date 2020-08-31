package com.mlacker.micros.report.domain.define

import com.mlacker.micros.report.domain.datasource.Expression
import com.mlacker.micros.report.domain.datasource.SourceTable

class Join(
    val id: Long,
    val primary: SourceTable,
    val foreign: SourceTable,
    val primaryColumn: Expression,
    val foreignColumn: Expression
)
