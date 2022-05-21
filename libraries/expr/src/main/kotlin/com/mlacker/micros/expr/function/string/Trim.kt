package com.mlacker.micros.expr.function.string

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.Function
import com.mlacker.micros.expr.Type
import com.mlacker.micros.expr.types.BasicType

class Trim(parameters: List<Expression>) : Function(parameters) {
    init {
        assertCount(parameters, 2)
        assertType(parameters, 0, BasicType.Text)
        assertType(parameters, 1, BasicType.Text)
    }

    override val type: Type = BasicType.Text

    override fun eval(variables: Map<String, Any>): Any? {
        val text = convertToValue<String>(BasicType.Text, 0, variables)!!
        return when (convertToValue<String>(BasicType.Text, 1, variables)!!) {
            "s" -> text.trimStart()
            "e" -> text.trimEnd()
            "b" -> text.trim()
            else -> throw RuntimeException("Trim第二个参数类型错误")
        }
    }

    override fun generate(variables: Map<String, Any>): String {
        return when (convertToValue<String>(BasicType.Text, 1, variables)!!) {
            "s" -> "LTRIM (${parameters[0].generate(variables)})"
            "e" -> "RTRIM (${parameters[0].generate(variables)})"
            "b" -> "LTRIM (RTRIM (${parameters[0].generate(variables)}))"
            else -> throw RuntimeException("Trim第二个参数类型错误")
        }
    }
}
