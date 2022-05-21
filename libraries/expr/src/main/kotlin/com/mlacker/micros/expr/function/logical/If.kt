package com.mlacker.micros.expr.function.logical

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.Function
import com.mlacker.micros.expr.Type
import com.mlacker.micros.expr.types.BasicType

class If(parameters: List<Expression>) : Function(parameters) {

    init {
        assertCount(parameters, 3)
        assertType(parameters, 0, BasicType.Boolean)
        assertType(parameters, 2, parameters[1].type)
    }


    override val type: Type = parameters[1].type

    override fun eval(variables: Map<String, Any>): Any? {
        return if (convertToValue(BasicType.Boolean, 0, variables)!!)
            parameters[1].eval(variables)
        else
            parameters[2].eval(variables)
    }

    override fun generate(variables: Map<String, Any>): String {
        return "IF(${parameters[0].generate(variables)}, ${parameters[1].generate(variables)}, ${parameters[2].generate(variables)})"
    }
}
