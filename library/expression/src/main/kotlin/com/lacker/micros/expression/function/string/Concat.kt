package com.lacker.micros.expression.function.string

import com.lacker.micros.expression.Expression
import com.lacker.micros.expression.core.Function
import com.lacker.micros.expression.Type

class Concat(parameters: List<Expression>) : Function(parameters) {

    override val sqlName: String
        get() = "CONCAT"

    override val type: Type
        get() = Type.String

    override fun eval(variables: Map<String, Any>): Any =
            parameters.map { it.eval(variables) }.joinToString("") { it as String }
}