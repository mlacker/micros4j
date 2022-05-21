package com.mlacker.micros.expr.function.mathematical

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.Function
import com.mlacker.micros.expr.Type
import com.mlacker.micros.expr.types.BasicType
import java.math.BigDecimal

class Mod(parameters: List<Expression>) : Function(parameters) {

    init {
        assertCount(parameters, 2)
        assertType(parameters, 0, BasicType.Decimal, BasicType.Integer)
        assertType(parameters, 1, BasicType.Decimal, BasicType.Integer)
    }

    override val type: Type = BasicType.Decimal

    override fun eval(variables: Map<String, Any>): Any? {
        return NumberUtils.convertReturnValue(
            convertToValue<BigDecimal>(BasicType.Decimal, 0, variables)!!
                .divideAndRemainder(convertToValue(BasicType.Decimal, 1, variables)!!)[1]
        )
    }

    override fun generate(variables: Map<String, Any>): String {
        return "MOD(${parameters[0].generate(variables)}, ${parameters[1].generate(variables)})"
    }
}
