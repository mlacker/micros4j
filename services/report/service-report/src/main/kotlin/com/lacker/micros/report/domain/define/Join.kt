package com.lacker.micros.report.domain.define

import com.lacker.micros.report.domain.datasource.*

class Join(
    val id: Long,
    val primary: SourceTable,
    val foreign: SourceTable,
    val primaryColumn: Expression,
    val foreignColumn: Expression
)
