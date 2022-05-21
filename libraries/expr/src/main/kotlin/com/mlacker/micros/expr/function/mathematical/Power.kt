package com.mlacker.micros.expr.function.mathematical

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.Function
import com.mlacker.micros.expr.types.BasicType
import java.math.BigDecimal

class Power(parameters: List<Expression>) : Function(parameters) {

    init {
        assertCount(parameters, 2)
        assertType(parameters, 0, BasicType.Decimal, BasicType.Integer)
        assertType(parameters, 1, BasicType.Integer)
    }

    override val type = BasicType.Decimal

    override fun eval(variables: Map<String, Any>): Any? {
        val number = convertToValue<BigDecimal>(BasicType.Decimal, 0, variables)!!
        val parameter = convertToValue<Long>(BasicType.Integer, 1, variables)!!
        return NumberUtils.convertReturnValue(number.pow(parameter.toInt()))
    }

    override fun generate(variables: Map<String, Any>): String {
        return "POWER(${parameters[0].generate(variables)}, ${parameters[1].generate(variables)})"
    }
}
