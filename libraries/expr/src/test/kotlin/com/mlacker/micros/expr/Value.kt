package com.mlacker.micros.expr

import com.mlacker.micros.expr.types.BasicType

class Value(private val value: Any = 1L) : Expression {

    override val type = BasicType.valueOf(value::class)

    override fun eval(variables: Map<String, Any>): Any = value

    override fun toString(): String = "$value"
}