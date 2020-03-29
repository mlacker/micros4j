package com.lacker.micros.expression.function.mathematical

import com.lacker.micros.expression.Expression
import com.lacker.micros.expression.core.Function
import com.lacker.micros.expression.Type
import kotlin.math.floor

class Floor(parameters: List<Expression>) : Function(parameters) {
    
    override val sqlName: String
        get() = "FLOOR"
    
    override val type: Type
        get() = Type.Number

    override fun eval(variables: Map<String, Any>): Any =
            when (val x = parameters[0].eval(variables)) {
                is Double -> floor(x)
                is Number -> floor(x.toDouble())
                else -> throw IllegalArgumentException()
            }
}