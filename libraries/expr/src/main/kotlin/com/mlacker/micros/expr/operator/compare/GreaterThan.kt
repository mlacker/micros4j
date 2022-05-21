package com.mlacker.micros.expr.operator.compare

import com.mlacker.micros.expr.Expression

class GreaterThan(leftOperand: Expression, rightOperand: Expression) : Compare(leftOperand, rightOperand) {

    override fun compare(order: Int): Boolean = order > 0

    override val symbol: String = ">"

    override val precedence: Int = 6
}
