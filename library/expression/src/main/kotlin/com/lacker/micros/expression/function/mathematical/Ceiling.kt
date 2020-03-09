package com.lacker.micros.expression.function.mathematical

import com.lacker.micros.expression.Expression
import com.lacker.micros.expression.core.Function
import com.lacker.micros.expression.Type
import kotlin.math.ceil

class Ceiling(parameters: List<Expression>) : Function(parameters) {

    override val sqlName: String
        get() = "CEILING"

    override val type: Type
        get() = Type.Number

    override fun eval(variables: Map<String, Any>): Any =
            when (val x = parameters[0].eval(variables)) {
                is Double -> ceil(x)
                is Number -> ceil(x.toDouble())
                else -> throw IllegalArgumentException()
            }
}