package com.mlacker.micros.expression.function.string

import com.mlacker.micros.expression.Expression
import com.mlacker.micros.expression.Type
import com.mlacker.micros.expression.core.Function

class Length(parameters: List<Expression>) : Function(parameters) {

    override val sqlName: String
        get() = "CHAR_LENGTH"

    override val type: Type
        get() = Type.Number

    override fun eval(variables: Map<String, Any>): Any =
            (parameters[0].eval(variables) as String).length

}