package com.mlacker.micros.expr.function.string

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.Function
import com.mlacker.micros.expr.Type
import com.mlacker.micros.expr.types.BasicType
import java.util.*

class Upper(parameters: List<Expression>) : Function(parameters) {

    init {
        assertCount(parameters, 1)
        assertType(parameters, 0, BasicType.Text)
    }

    override val type: Type = BasicType.Text

    override fun eval(variables: Map<String, Any>): Any? =
        convertToValue<String>(BasicType.Text, 0, variables)!!.uppercase(Locale.getDefault())

    override fun generate(variables: Map<String, Any>): String {
        return "UPPER(${parameters[0].generate(variables)})"
    }
}
