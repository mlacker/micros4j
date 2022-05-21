package com.mlacker.micros.expr.function.mathematical

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.Function
import com.mlacker.micros.expr.types.BasicType
import java.math.BigDecimal

class Max(parameters: List<Expression>) : Function(parameters) {


    override val type = BasicType.Any

    override fun eval(variables: Map<String, Any>): Any? {
        return NumberUtils.convertReturnValue(
            convertToValue<BigDecimal>(BasicType.Decimal, 0, variables)!!
                .max(convertToValue(BasicType.Decimal, 1, variables)!!)
        )
    }

    override fun generate(variables: Map<String, Any>): String {
        if (parameters.size == 1) {
            return "MAX(${parameters[0].generate(variables)})"
        }
        return "case when ${parameters[0].generate(variables)} > ${parameters[1].generate(variables)} then ${parameters[0].generate(variables)} else ${
            parameters[1].generate(
                variables
            )
        } end"
    }
}
