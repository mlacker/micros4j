package com.mlacker.micros.expr.function.string

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.Function
import com.mlacker.micros.expr.types.BasicType

class Contains(parameters: List<Expression>) : Function(parameters) {

    init {
        for (index in parameters.indices) {
            assertType(parameters, index, BasicType.Text)
        }
    }

    override val type = BasicType.Boolean

    override fun eval(variables: Map<String, Any>): Any? =
        convertToValue<String>(BasicType.Text, 0, variables)!!.contains(
            convertToValue<String>(BasicType.Text, 1, variables)!!
        )
}
