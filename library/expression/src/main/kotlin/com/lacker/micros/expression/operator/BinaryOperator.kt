package com.lacker.micros.expression.operator

import com.lacker.micros.expression.Expression

abstract class BinaryOperator(
    val leftOperand: Expression,
    val rightOperand: Expression
) : Expression {
    override fun toSql() = "${leftOperand.toSql()} ${symbol()} ${rightOperand.toSql()}"

    protected abstract fun symbol(): String
}