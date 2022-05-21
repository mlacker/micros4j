package com.mlacker.micros.expr.function.format

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.Function
import com.mlacker.micros.expr.Type
import com.mlacker.micros.expr.types.BasicType
import java.math.BigDecimal
import java.text.DecimalFormat

class FormatDecimal(parameters: List<Expression>) : Function(parameters) {
    init {
        assertCount(parameters, 3)
        assertType(parameters, 0, BasicType.Decimal)
        assertType(parameters, 1, BasicType.Integer)
        assertType(parameters, 2, BasicType.Boolean)
    }

    override val type: Type = BasicType.Text

    override fun eval(variables: Map<String, Any>): Any? {
        val number = convertToValue<BigDecimal>(BasicType.Decimal, 0, variables)!!
        val digits = convertToValue<Long>(BasicType.Integer, 1, variables)!!
        val thousandth = convertToValue<Boolean>(BasicType.Boolean, 2, variables)!!
        var formatStr = ""
        for (i in 1..digits) {
            formatStr += "0"
        }
        return if (thousandth)
            DecimalFormat("###,###.$formatStr").format(number)
        else
            DecimalFormat("######.$formatStr").format(number)
    }

    override fun generate(variables: Map<String, Any>): String {
        return "IF(${parameters[2].generate(variables)}, FORMAT(${parameters[0].generate(variables)},${parameters[1].generate(variables)}), CONVERT(${
            parameters[0].generate(
                variables
            )
        }, decimal(12, ${parameters[1].generate(variables)})))"
    }
}
