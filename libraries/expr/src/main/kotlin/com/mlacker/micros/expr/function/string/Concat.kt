package com.mlacker.micros.expr.function.string

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.Function
import com.mlacker.micros.expr.Type
import com.mlacker.micros.expr.types.BasicType

class Concat(parameters: List<Expression>) : Function(parameters) {

    init {
        parameters.forEachIndexed { i, _ -> assertType(parameters, i, BasicType.Text) }
    }

    override val type: Type = BasicType.Text

    override fun eval(variables: Map<String, Any>): Any? =
        parameters.indices.fold(StringBuilder()) { result, index ->
            result.append(
                convertToValue<String>(
                    BasicType.Text,
                    index,
                    variables
                )
            )
        }.toString()

    override fun generate(variables: Map<String, Any>): String {
        return parameters.joinToString(", ", "CONCAT(", ")") { it.generate(variables) }
    }
}
