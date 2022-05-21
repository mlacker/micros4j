package com.mlacker.micros.expr.function.convert

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.Function
import com.mlacker.micros.expr.types.BasicType

class IntegerToBoolean(parameters: List<Expression>) : Function(parameters) {

    init {
        assertCount(parameters, 1)
        assertType(parameters, 0, BasicType.Integer)
    }

    override val type = BasicType.Boolean

    override fun eval(variables: Map<String, Any>): Any? {
        val parameter = convertToValue<Long>(BasicType.Integer, 0, variables)!!
        return parameter != 0L
    }

    override fun generate(variables: Map<String, Any>): String {
        return "IF(${parameters[0].generate(variables)}, true, false)"
    }
}
