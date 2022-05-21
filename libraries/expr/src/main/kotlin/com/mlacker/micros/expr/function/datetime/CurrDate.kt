package com.mlacker.micros.expr.function.datetime

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.Function
import com.mlacker.micros.expr.types.BasicType
import java.time.LocalDate

class CurrDate(parameters: List<Expression>) : Function(parameters) {
    override val type = BasicType.Date

    override fun eval(variables: Map<String, Any>): Any? {
        return LocalDate.now()
    }

    override fun generate(variables: Map<String, Any>): String {
        return "left(now(), 10)"
    }
}
