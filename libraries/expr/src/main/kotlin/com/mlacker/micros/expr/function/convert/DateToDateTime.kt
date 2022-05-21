package com.mlacker.micros.expr.function.convert

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.Function
import com.mlacker.micros.expr.types.BasicType
import java.time.LocalDate

class DateToDateTime(parameters: List<Expression>) : Function(parameters) {

    init {
        assertCount(parameters, 1)
        assertType(parameters, 0, BasicType.Date)
    }

    override val type = BasicType.DateTime

    override fun eval(variables: Map<String, Any>): Any? {
        val localDate = convertToValue<LocalDate>(BasicType.Date, 0, variables)!!
        return localDate.atStartOfDay()
    }

    override fun generate(variables: Map<String, Any>): String {
        return "CONVERT(${parameters[0].generate(variables)}, DateTime)"
    }
}
