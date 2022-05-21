package com.mlacker.micros.expr.function.convert

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.Function
import com.mlacker.micros.expr.types.BasicType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class TextToDateTimeValidate(parameters: List<Expression>) : Function(parameters) {

    private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    init {
        assertCount(parameters, 1)
        assertType(parameters, 0, BasicType.Text)
    }

    override val type = BasicType.Boolean

    override fun eval(variables: Map<String, Any>): Any? {
        val dateStr = convertToValue<String>(BasicType.Text, 0, variables)!!
        return try {
            LocalDateTime.parse(dateStr, formatter).toLocalDate()
            true
        } catch (exception: DateTimeParseException) {
            false
        }
    }
}
