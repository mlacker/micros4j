package com.mlacker.micros.expression.operator

import com.mlacker.micros.expression.Expression

abstract class BinaryOperator(
    val leftOperand: Expression,
    val rightOperand: Expression
) : Expression {
    override fun toSql() = "${leftOperand.toSql()} ${symbol()} ${rightOperand.toSql()}"

    protected abstract fun symbol(): String
}