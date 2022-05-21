package com.mlacker.micros.expr.function.mathematical

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.Function
import com.mlacker.micros.expr.types.BasicType
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.pow

class Round(parameters: List<Expression>) : Function(parameters) {
    init {
        assertCount(parameters, 2)
        assertType(parameters, 0, BasicType.Decimal, BasicType.Integer)
        assertType(parameters, 1, BasicType.Integer)
    }

    override val type = BasicType.Decimal

    override fun eval(variables: Map<String, Any>): Any? {
        val number = convertToValue<BigDecimal>(BasicType.Decimal, 0, variables)!!
        val p2 = convertToValue<Long>(BasicType.Integer, 1, variables)!!
        val pow = BigDecimal(10.0.pow(p2.toInt()))
        return NumberUtils.convertReturnValue(
            number.multiply(pow).divide(pow, p2.toInt(), RoundingMode.HALF_UP)
        )
    }

    override fun generate(variables: Map<String, Any>): String {
        return "ROUND(${parameters[0].generate(variables)}, ${parameters[1].generate(variables)})"
    }
}
