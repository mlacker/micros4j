package com.mlacker.micros.expr.function.convert

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.Function
import com.mlacker.micros.expr.types.BasicType

class BooleanToInteger(parameters: List<Expression>) : Function(parameters) {

    init {
        assertCount(parameters, 1)
        assertType(parameters, 0, BasicType.Boolean)
    }

    override val type = BasicType.Integer

    override fun eval(variables: Map<String, Any>) = if (convertToValue(BasicType.Boolean, 0, variables)!!) 1L else 0L

    override fun generate(variables: Map<String, Any>): String {
        return "CONVERT(${parameters[0].generate(variables)}, SIGNED)"
    }

}
