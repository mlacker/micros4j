package com.mlacker.micros.expr.function.datetime

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.Function
import com.mlacker.micros.expr.Type
import com.mlacker.micros.expr.types.BasicType
import java.time.LocalDateTime

class Day(parameters: List<Expression>) : Function(parameters) {
    init {
        assertCount(parameters, 1)
        assertType(parameters, 0, BasicType.Date, BasicType.DateTime)
    }

    override val type: Type = BasicType.Integer

    override fun eval(variables: Map<String, Any>): Any {
        val dt = convertToValue<LocalDateTime>(BasicType.DateTime, 0, variables)!!
        return dt.dayOfMonth.toLong()
    }

    override fun generate(variables: Map<String, Any>): String {
        return "DAY(${parameters[0].generate(variables)})"
    }
}
