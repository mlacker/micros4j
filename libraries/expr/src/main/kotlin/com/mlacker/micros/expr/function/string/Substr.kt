package com.mlacker.micros.expr.function.string

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.Function
import com.mlacker.micros.expr.Type
import com.mlacker.micros.expr.types.BasicType

class Substr(parameters: List<Expression>) : Function(parameters) {
    init {
        assertCount(parameters, 3)
        assertType(parameters, 0, BasicType.Text)
        assertType(parameters, 1, BasicType.Integer)
        assertType(parameters, 2, BasicType.Integer)
    }

    override val type: Type = BasicType.Text

    override fun eval(variables: Map<String, Any>): Any? {
        val text = convertToValue<String>(BasicType.Text, 0, variables)!!
        val start = convertToValue<Long>(BasicType.Integer, 1, variables)!!
        val length = convertToValue<Long>(BasicType.Integer, 2, variables)!!
        return text.substring(start.toInt(), start.toInt() + length.toInt())
    }

    override fun generate(variables: Map<String, Any>): String {
        val sb = StringBuilder()
        sb.append("SUBSTR(${parameters[0].generate(variables)}, ")
        sb.append(Integer.parseInt(parameters[1].generate(variables)) + 1)
        if (parameters.size > 2) {
            sb.append(", ${parameters[2].generate(variables)}")
        }
        sb.append(")")
        return sb.toString()
    }
}
