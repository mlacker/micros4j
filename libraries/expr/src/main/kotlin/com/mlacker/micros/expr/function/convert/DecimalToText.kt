package com.mlacker.micros.expr.function.convert

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.Function
import com.mlacker.micros.expr.types.BasicType

class DecimalToText(parameters: List<Expression>) : Function(parameters) {

    init {
        assertCount(parameters, 1)
        assertType(parameters, 0, BasicType.Decimal)
    }

    override val type = BasicType.Text

    override fun eval(variables: Map<String, Any>): Any? = convertToValue<String>(BasicType.Text, 0, variables)!!

    override fun generate(variables: Map<String, Any>): String {
        return "CONVERT(${parameters[0].generate(variables)}, CHAR)"
    }


}
