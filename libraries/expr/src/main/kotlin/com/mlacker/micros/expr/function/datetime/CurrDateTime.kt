package com.mlacker.micros.expr.function.datetime

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.Function
import com.mlacker.micros.expr.types.BasicType
import java.time.LocalDateTime

class CurrDateTime(parameters: List<Expression>) : Function(parameters) {
    override val type = BasicType.DateTime

    override fun eval(variables: Map<String, Any>): Any? {
        return LocalDateTime.now()
    }

    override fun generate(variables: Map<String, Any>): String {
        return "now()"
    }
}
