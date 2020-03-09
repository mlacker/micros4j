package com.lacker.micros.expression.function.mathematical

import com.lacker.micros.expression.Expression
import com.lacker.micros.expression.core.Function
import com.lacker.micros.expression.Type
import kotlin.math.pow
import kotlin.math.round

class Round(parameters: List<Expression>) : Function(parameters) {

    override val sqlName: String
        get() = "ROUND"

    override val type: Type
        get() = Type.Number

    override fun eval(variables: Map<String, Any>): Any {
        val values = parameters.map { it.eval(variables) }

        val p1 = values[0] as Double
        val p2 = (values.getOrNull(1) ?: 0) as Number
        val power = 10.0.pow(p2.toInt())

        return round(p1 * power) / power
    }
}