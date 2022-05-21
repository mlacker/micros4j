package com.mlacker.micros.expr.function.string

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.Function
import com.mlacker.micros.expr.Type
import com.mlacker.micros.expr.types.BasicType

class Replace(parameters: List<Expression>) : Function(parameters) {

    init {
        assertCount(parameters, 3)
        assertType(parameters, 0, BasicType.Text)
        assertType(parameters, 1, BasicType.Text)
        assertType(parameters, 2, BasicType.Text)
    }

    override val type: Type = BasicType.Text

    override fun eval(variables: Map<String, Any>) =
        convertToValue<String>(BasicType.Text, 0, variables)!!.replace(
            convertToValue<String>(BasicType.Text, 1, variables)!!,
            convertToValue(BasicType.Text, 2, variables)!!
        )

    override fun generate(variables: Map<String, Any>): String {
        return parameters.joinToString(", ", "REPLACE(", ")") { it.generate(variables) }
    }
}
