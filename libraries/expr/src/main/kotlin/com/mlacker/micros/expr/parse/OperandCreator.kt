package com.mlacker.micros.expr.parse

import com.mlacker.micros.expr.Constant
import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.Variable
import com.mlacker.micros.expr.types.BasicType
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class OperandCreator {

    companion object {
        private val regexInteger = Regex("""^-?\d+$""")
        private val regexDecimal = Regex("""^-?\d+(?:\.\d+)$""")
        private val regexBoolean = Regex("""^true|false$""")
        private val regexDate = Regex("""^#\d{4}-\d{2}-\d{2}#$""")
        private val regexDatetime = Regex("""^#\d{4}-\d{2}-\d{2} \d{1,2}:\d{1,2}:\d{1,2}#$""")
        private val regexText = Regex("""^".*?"$""")
        private val regexVariable = Regex("""^[a-zA-Z_][a-zA-Z_\d]*$""")
        private val datetimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    }

    fun create(text: String): Expression {
        return when {
            regexInteger.matches(text) -> Constant(text.toLong(), BasicType.Integer)
            regexDecimal.matches(text) -> Constant(text.toBigDecimal(), BasicType.Decimal)
            regexBoolean.matches(text) -> Constant(text.toBoolean(), BasicType.Boolean)
            regexDate.matches(text) -> Constant(LocalDate.parse(text.trim('#')), BasicType.Date)
            regexDatetime.matches(text) -> Constant(
                LocalDateTime.parse(
                    text.trim('#'),
                    datetimeFormatter
                ), BasicType.DateTime
            )
            regexText.matches(text) -> Constant(text.trim('"'), BasicType.Text)
            regexVariable.matches(text) -> Variable(text, BasicType.Unknown)
            else -> throw IllegalArgumentException("Unsupported operand format, \"$text\"")
        }
    }
}
