package com.mlacker.micros.expression.function.date

import com.mlacker.micros.expression.Expression
import com.mlacker.micros.expression.Type
import com.mlacker.micros.expression.core.Function

class Days(parameters: List<Expression>) : Function(parameters) {

    override val sqlName = "TIMESTAMPDIFF"

    override val type get() = Type.Date

    override fun eval(variables: Map<String, Any>): Any {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}