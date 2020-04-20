package com.lacker.micros.expression.function.date

import com.lacker.micros.expression.Expression
import com.lacker.micros.expression.core.Function
import com.lacker.micros.expression.Type

class Days(parameters: List<Expression>) : Function(parameters) {

    override val sqlName = "TIMESTAMPDIFF"

    override val type get() = Type.Date

    override fun eval(variables: Map<String, Any>): Any {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}