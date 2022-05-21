package com.mlacker.micros.expr.function.convert

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.Function
import com.mlacker.micros.expr.types.BasicType

class BooleanToText(parameters: List<Expression>) : Function(parameters) {

    init {
        Boolean
        assertCount(parameters, 1)
        assertType(parameters, 0, BasicType.Boolean)
    }

    override val type = BasicType.Text

    override fun eval(variables: Map<String, Any>): Any? {
        val parameter = convertToValue<Boolean>(BasicType.Boolean, 0, variables)!!
        return if (parameter) "'true'" else "'false'"
    }

    override fun generate(variables: Map<String, Any>): String {
        return "CONVERT(${parameters[0].generate(variables)}, CHAR)"
    }
}
