package com.mlacker.micros.expr.function.mathematical

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.Function
import com.mlacker.micros.expr.types.BasicType
import java.math.BigDecimal
import java.math.RoundingMode

class Ceiling(parameters: List<Expression>) : Function(parameters) {

    init {
        assertCount(parameters, 2)
        assertType(parameters, 0, BasicType.Decimal, BasicType.Integer)
        assertType(parameters, 1, BasicType.Integer)
    }

    override val type = BasicType.Decimal

    override fun eval(variables: Map<String, Any>): Any? {
        return NumberUtils.convertReturnValue(
            convertToValue<BigDecimal>(BasicType.Decimal, 0, variables)!!
                .setScale(
                    (convertToValue<Long>(BasicType.Integer, 1, variables)!!).toInt(), RoundingMode.UP
                )
        )
    }

    override fun generate(variables: Map<String, Any>): String {
        return "TRUNCATE(CEIL(${parameters[0].generate(variables)} * POWER(10, ${parameters[1].generate(variables)})) / POWER(10, ${
            parameters[1].generate(
                variables
            )
        }), ${parameters[1].generate(variables)})"
    }
}
