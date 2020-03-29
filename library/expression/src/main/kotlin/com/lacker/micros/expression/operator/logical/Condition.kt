package com.lacker.micros.expression.operator.logical

import com.lacker.micros.expression.Expression
import com.lacker.micros.expression.Type

class Condition(
    private val condition: Expression,
    private val left: Expression,
    private val right: Expression
) : Expression {

    override val type: Type = left.type

    override fun eval(variables: Map<String, Any>): Any =
        if (condition.eval(variables) as Boolean) left.eval(variables) else right.eval(variables)

    override fun toSql(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}