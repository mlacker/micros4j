package com.lacker.micros.expression.operator.logical

import com.lacker.micros.expression.Expression
import com.lacker.micros.expression.Type
import com.lacker.micros.expression.operator.BinaryOperator

class And(leftOperand: Expression, rightOperand: Expression) : BinaryOperator(leftOperand, rightOperand) {

    override fun symbol(): String = "AND"

    override val type: Type = Type.Boolean

    override fun eval(variables: Map<String, Any>): Any =
        leftOperand.eval(variables) as Boolean && rightOperand.eval(variables) as Boolean
}