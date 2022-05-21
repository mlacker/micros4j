package com.mlacker.micros.expr.function.convert

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.Function
import com.mlacker.micros.expr.types.BasicType
import java.math.BigDecimal

class IntegerToDecimal(parameters: List<Expression>) : Function(parameters) {

    init {
        assertCount(parameters, 1)
        assertType(parameters, 0, BasicType.Integer)
    }

    override val type = BasicType.Decimal

    override fun eval(variables: Map<String, Any>): Any? {
        val parameter = convertToValue<Long>(BasicType.Integer, 0, variables)!!
        return BigDecimal(parameter)
    }

    override fun generate(variables: Map<String, Any>): String {
        return "CONVERT(${parameters[0].generate(variables)}, Decimal)"
    }
}
