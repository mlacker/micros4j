package com.mlacker.micros.expr.function.convert

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.Function
import com.mlacker.micros.expr.types.BasicType
import java.time.LocalDateTime

class DateTimeToDate(parameters: List<Expression>) : Function(parameters) {

    init {
        assertCount(parameters, 1)
        assertType(parameters, 0, BasicType.DateTime)
    }

    override val type = BasicType.Date

    override fun eval(variables: Map<String, Any>): Any? {
        val localDateTime = convertToValue<LocalDateTime>(BasicType.DateTime, 0, variables)!!
        return localDateTime.toLocalDate()
    }

    override fun generate(variables: Map<String, Any>): String {
        return "CONVERT(${parameters[0].generate(variables)}, Date)"
    }
}
