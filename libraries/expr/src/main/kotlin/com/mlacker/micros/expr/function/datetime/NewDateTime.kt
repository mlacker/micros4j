package com.mlacker.micros.expr.function.datetime

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.Function
import com.mlacker.micros.expr.Type
import com.mlacker.micros.expr.types.BasicType
import java.time.LocalDateTime

class NewDateTime(parameters: List<Expression>) : Function(parameters) {
    init {
        assertCount(parameters, 6)
        assertType(parameters, 0, BasicType.Integer)
        assertType(parameters, 1, BasicType.Integer)
        assertType(parameters, 2, BasicType.Integer)
        assertType(parameters, 3, BasicType.Integer)
        assertType(parameters, 4, BasicType.Integer)
        assertType(parameters, 5, BasicType.Integer)
    }

    override val type: Type = BasicType.DateTime

    override fun eval(variables: Map<String, Any>): Any? {
        val values = parameters.map { it.eval(variables) }
        val year = values[0] as Long
        val month = values[1] as Long
        val day = values[2] as Long
        val hour = values[3] as Long
        val minute = values[4] as Long
        val second = values[5] as Long
        return LocalDateTime.of(year.toInt(), month.toInt(), day.toInt(), hour.toInt(), minute.toInt(), second.toInt())
    }

    override fun generate(variables: Map<String, Any>): String {
        return "STR_TO_DATE(CONCAT(${parameters[0].generate(variables)}, '-', ${parameters[1].generate(variables)}, '-', ${parameters[2].generate(variables)}, ' ', ${
            parameters[3].generate(
                variables
            )
        }, ':', ${parameters[4].generate(variables)}, ':', ${parameters[5].generate(variables)}), '%Y-%m-%d %H:%i:%s')"
    }
}
