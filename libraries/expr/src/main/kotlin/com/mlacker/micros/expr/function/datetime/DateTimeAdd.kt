package com.mlacker.micros.expr.function.datetime

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.Function
import com.mlacker.micros.expr.Type
import com.mlacker.micros.expr.exception.ExpressionTypeException
import com.mlacker.micros.expr.types.BasicType
import java.time.LocalDate
import java.time.LocalDateTime

class DateTimeAdd(parameters: List<Expression>) : Function(parameters) {
    init {
        assertCount(parameters, 3)
        assertType(parameters, 0, BasicType.Date, BasicType.DateTime)
        assertType(parameters, 1, BasicType.Integer)
        assertType(parameters, 2, BasicType.Text)
    }

    override val type: Type = parameters[0].type

    override fun eval(variables: Map<String, Any>): Any? {
        val date: LocalDate
        val datetime: LocalDateTime?

        val number = convertToValue<Long>(BasicType.Integer, 1, variables)!!
        val part = convertToValue<String>(BasicType.Text, 2, variables)!!

        return if (parameters[0].type == BasicType.Date) {
            date = convertToValue(BasicType.Date, 0, variables)!!

            when (part) {
                "y" -> date.plusYears(number)
                "mo" -> date.plusMonths(number)
                "w" -> date.plusWeeks(number)
                "d" -> date.plusDays(number)
                else -> throw ExpressionTypeException("第3个参数类型错误，只能为以下值：'y'、'mo'、'w'、'd'、'h'、'mi'、's'。")
            }
        } else {
            datetime = convertToValue(BasicType.DateTime, 0, variables)!!

            when (part) {
                "y" -> datetime.plusYears(number)
                "mo" -> datetime.plusMonths(number)
                "w" -> datetime.plusWeeks(number)
                "d" -> datetime.plusDays(number)
                "h" -> datetime.plusHours(number)
                "mi" -> datetime.plusMinutes(number)
                "s" -> datetime.plusSeconds(number)
                else -> ExpressionTypeException("第3个参数类型错误，只能为以下值：'y'、'mo'、'w'、'd'、'h'、'mi'、's'。")
            }
        }
    }

    override fun generate(variables: Map<String, Any>): String {
        val part = convertToValue<String>(BasicType.Text, 2, variables)!!
        val unit = when (part) {
            "s" -> "SECOND"
            "mi" -> "MINUTE"
            "h" -> "HOUR"
            "d" -> "DAY"
            "w" -> "WEEK"
            "mo" -> "MONTH"
            "y" -> "YEAR"
            else -> ExpressionTypeException("第3个参数类型错误，只能为以下值：'y'、'mo'、'w'、'd'、'h'、'mi'、's'。")
        }
        return "DATE_ADD(${parameters[0].generate(variables)}, INTERVAL ${parameters[1].generate(variables)} $unit)"
    }

}
