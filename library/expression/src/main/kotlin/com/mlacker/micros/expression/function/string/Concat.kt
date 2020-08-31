package com.mlacker.micros.expression.function.string

import com.mlacker.micros.expression.Expression
import com.mlacker.micros.expression.Type
import com.mlacker.micros.expression.core.Function

class Concat(parameters: List<Expression>) : Function(parameters) {

    override val sqlName: String
        get() = "CONCAT"

    override val type: Type
        get() = Type.String

    override fun eval(variables: Map<String, Any>): Any =
            parameters.map { it.eval(variables) }.joinToString("") { it as String }
}