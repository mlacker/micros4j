package com.lacker.micros.expression.core

import com.lacker.micros.expression.Expression

abstract class Function(val parameters: List<Expression>) : Expression {

    protected abstract val sqlName: String

    override fun toSql(): String = parameters.joinToString(
            separator = ", ",
            prefix = "$sqlName(",
            postfix = ")"
    ) { it.toSql() }
}