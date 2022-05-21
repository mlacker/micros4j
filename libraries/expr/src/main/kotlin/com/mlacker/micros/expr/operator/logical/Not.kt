package com.mlacker.micros.expr.operator.logical

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.Type
import com.mlacker.micros.expr.types.BasicType

class Not(internal val operand: Expression) : Expression {

    override val type: Type = BasicType.Boolean

    override fun eval(variables: Map<String, Any>): Any? = !(operand.eval(variables) as Boolean)

    override fun toString(): String = "!$operand"

    override fun generate(variables: Map<String, Any>): String {
        return "not ${operand.generate(variables)}"
    }
}
