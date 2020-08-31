package com.mlacker.micros.expression.operator.logical

import com.mlacker.micros.expression.Expression
import com.mlacker.micros.expression.Type
import com.mlacker.micros.expression.operator.BinaryOperator

class Or(leftOperand: Expression, rightOperand: Expression) : BinaryOperator(leftOperand, rightOperand) {

    override fun symbol(): String = "OR"

    override val type: Type = Type.Boolean

    override fun eval(variables: Map<String, Any>): Any =
        leftOperand.eval(variables) as Boolean || rightOperand.eval(variables) as Boolean
}