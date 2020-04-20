package com.lacker.micros.expression.operator.logical

import com.lacker.micros.expression.Expression
import com.lacker.micros.expression.Type

class Not(val operand: Expression) : Expression {

    override val type: Type = Type.Boolean

    override fun eval(variables: Map<String, Any>): Any = !(operand.eval(variables) as Boolean)

    override fun toSql(): String = "NOT ${operand.toSql()}"
}