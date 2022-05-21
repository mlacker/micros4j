package com.mlacker.micros.expr.operator

import com.mlacker.micros.expr.Expression

abstract class BinaryOperator(
    val leftOperand: Expression,
    val rightOperand: Expression
) : Expression {

    internal abstract val symbol: String

    internal abstract val precedence: Int

    override fun toString() =
        generate { it.toString() }

    override fun generate(variables: Map<String, Any>) =
        generate { it.generate(variables) }

    private fun generate(convert: (Expression) -> String): String {
        var leftText = convert(leftOperand)
        if (leftOperand is BinaryOperator && leftOperand.precedence > this.precedence) {
            leftText = "($leftText)"
        }
        var rightText = convert(rightOperand)
        if (rightOperand is BinaryOperator && rightOperand.precedence > this.precedence) {
            rightText = "($rightText)"
        }

        return "$leftText $symbol $rightText"
    }
}
