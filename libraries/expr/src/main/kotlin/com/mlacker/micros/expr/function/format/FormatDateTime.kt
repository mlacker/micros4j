package com.mlacker.micros.expr.function.format

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.Function
import com.mlacker.micros.expr.Type
import com.mlacker.micros.expr.types.BasicType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class FormatDateTime(parameters: List<Expression>) : Function(parameters) {
    init {
        assertCount(parameters, 2)
        assertType(parameters, 0, BasicType.Date, BasicType.DateTime)
        assertType(parameters, 1, BasicType.Text)
    }

    override val type: Type = BasicType.Text

    override fun eval(variables: Map<String, Any>): Any? {
        val dt = convertToValue<LocalDateTime>(BasicType.DateTime, 0, variables)!!
        val format = convertToValue<String>(BasicType.Text, 1, variables)!!
        return dt.format(DateTimeFormatter.ofPattern(format))
    }

    override fun generate(variables: Map<String, Any>): String {
        var format = parameters[1].generate(variables)
        var flag = false
        if (format.contains("m")) {
            flag = true
        }
        format = format.replace("yyyy", "%Y")
        format = format.replace("yy", "%y")
        format = format.replace("MM", "%m")
        format = format.replace("M", "%c")
        if (format.contains("dd")) {
            format = format.replace("dd", "%d")
        } else if (format.contains("d") && !format.contains("%d")) {
            format = format.replace("d", "%e")
        }
        if (format.contains("hh")) {
            format = format.replace("hh", "%l")
        } else if (format.contains("h") && !format.contains("%l")) {
            format = format.replace("h", "%k")
        }
        if (format.contains("HH")) {
            format = format.replace("HH", "%H")
        } else if (format.contains("H") && !format.contains("%H")) {
            format = format.replace("H", "%h")
        }
        if (format.contains("mm")) {
            format = format.replace("mm", "%i")
        } else if (format.contains("m") && !format.contains("%i") && flag) {
            format = format.reversed()
            format = format.replaceFirst("m", "i%")
            format = format.reversed()
        }
        if (format.contains("ss")) {
            format = format.replace("ss", "%s")
        } else if (format.contains("s") && !format.contains("%s")) {
            format = format.replace("s", "%s")
        }
        return "DATE_FORMAT(${parameters[0].generate(variables)}, $format)"
    }
}
