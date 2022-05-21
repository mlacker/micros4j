package com.mlacker.micros.expr.function.convert

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.Function
import com.mlacker.micros.expr.types.BasicType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TextToDateTime(parameters: List<Expression>) : Function(parameters) {

    private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    init {
        assertCount(parameters, 1)
        assertType(parameters, 0, BasicType.Text)
    }

    override val type = BasicType.DateTime

    override fun eval(variables: Map<String, Any>): Any? {
        val dateStr = convertToValue<String>(BasicType.Text, 0, variables)!!
        return LocalDateTime.from(formatter.parse(dateStr))
    }

    override fun generate(variables: Map<String, Any>): String {
        return "CONVERT(${parameters[0].generate(variables)}, DATETIME)"
    }
}
