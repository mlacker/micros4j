package com.mlacker.micros.expr.function.string

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.Function
import com.mlacker.micros.expr.Type
import com.mlacker.micros.expr.types.BasicType

class Length(parameters: List<Expression>) : Function(parameters) {

    init {
        assertCount(parameters, 1)
        assertType(parameters, 0, BasicType.Text)
    }

    override val type: Type = BasicType.Integer

    override fun eval(variables: Map<String, Any>): Any? {
        return (convertToValue<String>(BasicType.Text, 0, variables))!!.length.toLong()
    }

    override fun generate(variables: Map<String, Any>): String {
        return "LENGTH(${parameters[0].generate(variables)})"
    }

}
