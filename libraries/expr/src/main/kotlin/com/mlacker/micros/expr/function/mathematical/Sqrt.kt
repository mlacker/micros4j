package com.mlacker.micros.expr.function.mathematical

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.Function
import com.mlacker.micros.expr.types.BasicType
import java.math.BigDecimal
import kotlin.math.sqrt

/**
 * 求取平方根
 */
class Sqrt(parameters: List<Expression>) : Function(parameters) {

    init {
        assertCount(parameters, 1)
        assertType(parameters, 0, BasicType.Decimal, BasicType.Integer)
    }

    override val type = BasicType.Decimal

    override fun eval(variables: Map<String, Any>): Any? {
        val parameter = convertToValue<BigDecimal>(BasicType.Decimal, 0, variables)!!
        return NumberUtils.convertReturnValue(sqrt((NumberUtils.getDouble(parameter))))
    }

    override fun generate(variables: Map<String, Any>): String {
        return "SQRT(${parameters[0].generate(variables)})"
    }
}
