package com.mlacker.micros.expression.core

import com.mlacker.micros.expression.Expression
import com.mlacker.micros.expression.Type

class Variable(private val key: String, override val type: Type) : Expression {

    init {
        if (key.isBlank()) throw IllegalArgumentException("key cannot be null")
    }

    override fun eval(variables: Map<String, Any>): Any = variables[key]
            ?: throw IllegalArgumentException("variable must be exists")

    override fun toSql(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}