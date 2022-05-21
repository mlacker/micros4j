package com.mlacker.micros.expr.function.mathematical

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.Function
import com.mlacker.micros.expr.types.BasicType

class Count(parameters: List<Expression>) : Function(parameters) {

    init {
        assertCount(parameters, 1)
    }

    override val type = BasicType.Any

    override fun eval(variables: Map<String, Any>): Any? {
        throw NotImplementedError("$this is NotImplemented")
    }

    override fun generate(variables: Map<String, Any>): String {
        return "COUNT(${parameters[0].generate(variables)})"
    }
}
