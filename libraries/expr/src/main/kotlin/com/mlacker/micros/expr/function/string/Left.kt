package com.mlacker.micros.expr.function.string

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.Function
import com.mlacker.micros.expr.Type
import com.mlacker.micros.expr.types.BasicType

class Left(parameters: List<Expression>) : Function(parameters) {

    init {
        assertCount(parameters, 2)
        assertType(parameters, 0, BasicType.Text)
        assertType(parameters, 1, BasicType.Integer)
    }

    override val type: Type = BasicType.Text

    override fun eval(variables: Map<String, Any>): Any? {
        val text = convertToValue<String>(BasicType.Text, 0, variables)!!
        val length = convertToValue<Long>(BasicType.Integer, 1, variables)!!
        return text.substring(0, length.toInt())
    }

    override fun generate(variables: Map<String, Any>): String {
        return "left(${parameters[0].generate(variables)}, ${parameters[1].generate(variables)})"
    }
}
