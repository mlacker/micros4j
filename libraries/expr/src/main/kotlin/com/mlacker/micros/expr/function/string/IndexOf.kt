package com.mlacker.micros.expr.function.string

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.Function
import com.mlacker.micros.expr.Type
import com.mlacker.micros.expr.types.BasicType

class IndexOf(parameters: List<Expression>) : Function(parameters) {
    init {
        assertCount(parameters, 3)
        assertType(parameters, 0, BasicType.Text)
        assertType(parameters, 1, BasicType.Text)
        assertType(parameters, 2, BasicType.Boolean)
    }

    override val type: Type = BasicType.Integer

    override fun eval(variables: Map<String, Any>): Any? {
        val text = convertToValue<String>(BasicType.Text, 0, variables)!!
        val search = convertToValue<String>(BasicType.Text, 1, variables)!!
        val ignoreCase = convertToValue(BasicType.Boolean, 2, variables) ?: false
        return text.indexOf(search, ignoreCase = ignoreCase).toLong()
    }

    override fun generate(variables: Map<String, Any>): String {
        return "INSTR(${parameters[0].generate(variables)}, ${parameters[1].generate(variables)})"
    }
}
