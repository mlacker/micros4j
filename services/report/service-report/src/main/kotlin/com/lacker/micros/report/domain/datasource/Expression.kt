package com.lacker.micros.report.domain.datasource

import net.sf.jsqlparser.expression.Expression

interface Expression {

    fun sql(): Expression
}