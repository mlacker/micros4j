package com.mlacker.micros.expr.function.mathematical

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.Function
import com.mlacker.micros.expr.types.BasicType
import java.math.BigDecimal
import kotlin.math.abs

class Abs(parameters: List<Expression>) : Function(parameters) {
    init {
        assertCount(parameters, 1)
        assertType(parameters, 0, BasicType.Integer, BasicType.Decimal)
    }

    override val type = BasicType.Decimal

    override fun eval(variables: Map<String, Any>): Any? =
        when (val x = parameters[0].eval(variables)) {
            is Long -> abs(x)
            is BigDecimal -> x.abs()
            else -> TODO()
        }

    override fun generate(variables: Map<String, Any>): String {
        return "ABS(${parameters[0].generate(variables)})"
    }
}
