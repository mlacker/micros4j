package com.mlacker.micros.expr.operator.compare

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.Type
import com.mlacker.micros.expr.operator.BinaryOperator
import com.mlacker.micros.expr.types.BasicType
import java.util.*

class Equals(leftOperand: Expression, rightOperand: Expression) : BinaryOperator(leftOperand, rightOperand) {

    override val type: Type = BasicType.Boolean

    override fun eval(variables: Map<String, Any>): Any =
        Objects.equals(leftOperand.eval(variables), rightOperand.eval(variables))

    override val symbol: String = "=="

    override val precedence: Int = 7

    override fun generate(variables: Map<String, Any>): String {
        return "${leftOperand.generate(variables)} = ${rightOperand.generate(variables)}"
    }
}
