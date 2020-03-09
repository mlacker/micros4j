package com.lacker.micros.expression.function.string

import com.lacker.micros.expression.Expression
import com.lacker.micros.expression.core.Function
import com.lacker.micros.expression.Type

class Length(parameters: List<Expression>) : Function(parameters) {

    override val sqlName: String
        get() = "CHAR_LENGTH"

    override val type: Type
        get() = Type.Number

    override fun eval(variables: Map<String, Any>): Any =
            (parameters[0].eval(variables) as String).length

}