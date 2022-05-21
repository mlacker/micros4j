package com.mlacker.micros.expr.function.format

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.Function
import com.mlacker.micros.expr.Type
import com.mlacker.micros.expr.types.BasicType
import java.math.BigDecimal
import java.text.DecimalFormat

class FormatPercent(parameters: List<Expression>) : Function(parameters) {
    init {
        assertCount(parameters, 2)
        assertType(parameters, 0, BasicType.Decimal)
        assertType(parameters, 1, BasicType.Integer)
    }


    override val type: Type = BasicType.Text

    override fun eval(variables: Map<String, Any>): Any? {
        val number = convertToValue<BigDecimal>(BasicType.Decimal, 0, variables)!!
        val digits = convertToValue<Long>(BasicType.Integer, 1, variables)!!
        var formatStr = ""
        for (i in 1..digits) {
            formatStr += "0"
        }
        return DecimalFormat("######.$formatStr").format(number * BigDecimal(100)) + "%"
    }

    override fun generate(variables: Map<String, Any>): String {
        return "CONCAT(TRUNCATE(${parameters[0].generate(variables)} * 100, ${parameters[1].generate(variables)}), '%')"
    }
}
