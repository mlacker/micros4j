package com.mlacker.micros.expr.operator.logical

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.Type

class Condition(
    private val condition: Expression,
    private val left: Expression,
    private val right: Expression
) : Expression {

    override val type: Type = left.type

    override fun eval(variables: Map<String, Any>): Any? =
        if (condition.eval(variables) as Boolean) left.eval(variables) else right.eval(variables)
}
