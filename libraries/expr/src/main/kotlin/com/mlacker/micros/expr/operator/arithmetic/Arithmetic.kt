package com.mlacker.micros.expr.operator.arithmetic

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.operator.BinaryOperator
import com.mlacker.micros.expr.types.BasicType
import java.math.BigDecimal

abstract class Arithmetic(leftOperand: Expression, rightOperand: Expression) : BinaryOperator(leftOperand, rightOperand) {

    final override val type =
        if (leftOperand.type == BasicType.Decimal || rightOperand.type == BasicType.Decimal) BasicType.Decimal
        else BasicType.Integer

    final override fun eval(variables: Map<String, Any>): Any? {
        var left: Number = leftOperand.eval(variables) as Number
        var right: Number = rightOperand.eval(variables) as Number

        return if (left is Long && right is Long) {
            eval(left, right)
        } else {
            if (left is Long) {
                left = left.toBigDecimal()
            }
            if (right is Long) {
                right = right.toBigDecimal()
            }

            eval(left as BigDecimal, right as BigDecimal)
        }
    }

    protected abstract fun eval(left: Long, right: Long): Long

    protected abstract fun eval(left: BigDecimal, right: BigDecimal): BigDecimal
}
