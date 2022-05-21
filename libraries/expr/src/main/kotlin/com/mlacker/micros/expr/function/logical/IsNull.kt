package com.mlacker.micros.expr.function.logical

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.Function
import com.mlacker.micros.expr.types.BasicType

class IsNull(parameters: List<Expression>) : Function(parameters) {

    init {
        assertCount(parameters, 1)
    }

    override val type = BasicType.Boolean

    override fun eval(variables: Map<String, Any>): Any? = parameters[0].eval(variables) == null

    override fun generate(variables: Map<String, Any>): String {
        return "${parameters[0].generate(variables)} is null"
    }
}
