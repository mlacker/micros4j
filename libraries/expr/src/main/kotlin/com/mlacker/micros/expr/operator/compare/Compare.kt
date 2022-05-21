package com.mlacker.micros.expr.operator.compare

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.Type
import com.mlacker.micros.expr.operator.BinaryOperator
import com.mlacker.micros.expr.types.BasicType
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime

abstract class Compare(leftOperand: Expression, rightOperand: Expression) : BinaryOperator(leftOperand, rightOperand) {

    final override val type = BasicType.Boolean

    final override fun eval(variables: Map<String, Any>): Boolean {
        val leftType = determineTypeIfUnknown(leftOperand, variables)
        val rightType = determineTypeIfUnknown(rightOperand, variables)

        if ((leftType == BasicType.Integer || leftType == BasicType.Decimal)
            && (rightType == BasicType.Integer || rightType == BasicType.Decimal)
        ) {
            var left: Number = leftOperand.eval(variables) as Number
            var right: Number = rightOperand.eval(variables) as Number

            return if (left is Long && right is Long) {
                compare(left.compareTo(right))
            } else {
                if (left is Long) {
                    left = left.toBigDecimal()
                }
                if (right is Long) {
                    right = right.toBigDecimal()
                }

                compare((left as BigDecimal).compareTo(right as BigDecimal))
            }
        }

        // FIXME: 有没有更好的写法？
        if (leftType == BasicType.DateTime) {
            val left = leftOperand.eval(variables) as LocalDateTime

            if (rightType == BasicType.Date) {
                return compare(left.compareTo((rightOperand.eval(variables) as LocalDate).atStartOfDay()))
            } else if (rightType == BasicType.DateTime) {
                return compare(left.compareTo(rightOperand.eval(variables) as LocalDateTime))
            }
        }

        if (leftType == BasicType.Date) {
            val left = leftOperand.eval(variables) as LocalDate

            if (rightType == BasicType.Date) {
                return compare(left.compareTo((rightOperand.eval(variables) as LocalDate)))
            } else if (rightType == BasicType.DateTime) {
                return compare(left.atStartOfDay().compareTo(rightOperand.eval(variables) as LocalDateTime))
            }
        }

        throw UnsupportedOperationException("Unsupported arguments type evaluation")
    }

    private fun determineTypeIfUnknown(operand: Expression, variables: Map<String, Any>): Type {
        var type = operand.type
        if (type == BasicType.Unknown) {
            val value = operand.eval(variables) ?: return BasicType.Unknown
            type = BasicType.valueOf(value::class)
        }
        return type
    }

    protected abstract fun compare(order: Int): Boolean
}
