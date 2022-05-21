package com.mlacker.micros.expr.function.convert

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.Function
import com.mlacker.micros.expr.types.BasicType
import java.math.BigDecimal

class DecimalToInteger(parameters: List<Expression>) : Function(parameters) {

    init {
        assertCount(parameters, 1)
        assertType(parameters, 0, BasicType.Decimal)
    }

    override val type = BasicType.Integer

    override fun eval(variables: Map<String, Any>): Any? {
        val parameter = convertToValue<BigDecimal>(BasicType.Decimal, 0, variables)!!
        return parameter.toLong()
    }

    override fun generate(variables: Map<String, Any>): String {
        return "CONVERT(${parameters[0].generate(variables)}, SIGNED)"
    }
}
