package com.mlacker.micros.expr.operator.logical

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.Type
import com.mlacker.micros.expr.operator.BinaryOperator
import com.mlacker.micros.expr.types.BasicType

class Or(leftOperand: Expression, rightOperand: Expression) : BinaryOperator(leftOperand, rightOperand) {

    override val type: Type = BasicType.Boolean

    override fun eval(variables: Map<String, Any>): Any =
        leftOperand.eval(variables) as Boolean || rightOperand.eval(variables) as Boolean

    override val symbol: String = "|"

    override val precedence: Int = 12

    override fun generate(variables: Map<String, Any>): String {
        return "${leftOperand.generate(variables)} or ${rightOperand.generate(variables)}"
    }
}
