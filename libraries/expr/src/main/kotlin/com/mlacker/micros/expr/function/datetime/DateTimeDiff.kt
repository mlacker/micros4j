package com.mlacker.micros.expr.function.datetime

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.Function
import com.mlacker.micros.expr.Type
import com.mlacker.micros.expr.types.BasicType
import java.time.Duration
import java.time.LocalDateTime

class DateTimeDiff(parameters: List<Expression>) : Function(parameters) {
    init {
        assertCount(parameters, 3)
        assertType(parameters, 0, BasicType.Date, BasicType.DateTime)
        assertType(parameters, 1, BasicType.Date, BasicType.DateTime)
        assertType(parameters, 2, BasicType.Text)
    }


    override val type: Type = BasicType.Integer

    override fun eval(variables: Map<String, Any>): Any? {

        val dt1 = convertToValue<LocalDateTime>(BasicType.DateTime, 0, variables)!!
        val dt2 = convertToValue<LocalDateTime>(BasicType.DateTime, 1, variables)!!
        val typeStr = convertToValue<String>(BasicType.Text, 2, variables)!!
        val duration = Duration.between(dt1, dt2)
        return when (typeStr) {
            "y" -> (duration.toDays() / 365)
            "mo" -> (duration.toDays() / 30)
            "w" -> (duration.toDays() / 7)
            "d" -> (duration.toDays())
            "h" -> (duration.toHours())
            "mi" -> (duration.toMinutes())
            "s" -> (duration.toMillis() / 1000)
            else -> throw Exception("第三个参数类型错误")
        }
    }

    override fun generate(variables: Map<String, Any>): String {
        val typeStr = convertToValue<String>(BasicType.Text, 2, variables)!!
        val type = when (typeStr) {
            "y" -> "YEAR"
            "mo" -> "MONTH"
            "w" -> "WEEK"
            "d" -> "DAY"
            "h" -> "HOUR"
            "mi" -> "MINUTE"
            "s" -> "SECOND"
            else -> throw Exception("第三个参数类型错误")
        }
        return "TIMESTAMPDIFF($type, ${parameters[0].generate(variables)}, ${parameters[1].generate(variables)})"
    }
}
