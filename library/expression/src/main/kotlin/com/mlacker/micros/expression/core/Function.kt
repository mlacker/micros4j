package com.mlacker.micros.expression.core

import com.mlacker.micros.expression.Expression

abstract class Function(val parameters: List<Expression>) : Expression {

    protected abstract val sqlName: String

    override fun toSql(): String = parameters.joinToString(
            separator = ", ",
            prefix = "$sqlName(",
            postfix = ")"
    ) { it.toSql() }
}