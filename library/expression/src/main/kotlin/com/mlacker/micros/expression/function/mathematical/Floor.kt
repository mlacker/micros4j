package com.mlacker.micros.expression.function.mathematical

import com.mlacker.micros.expression.Expression
import com.mlacker.micros.expression.Type
import com.mlacker.micros.expression.core.Function
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