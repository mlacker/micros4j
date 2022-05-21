package com.mlacker.micros.expr.function.convert

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.Function
import com.mlacker.micros.expr.types.BasicType

class TextToIntegerValidate(parameters: List<Expression>) : Function(parameters) {
    private val regexInteger = Regex("""^-?\d+$""")

    init {
        assertCount(parameters, 1)
        assertType(parameters, 0, BasicType.Text)
    }

    override val type = BasicType.Boolean

    override fun eval(variables: Map<String, Any>): Any? =
        regexInteger.matches(convertToValue<String>(BasicType.Text, 0, variables)!!)
}
