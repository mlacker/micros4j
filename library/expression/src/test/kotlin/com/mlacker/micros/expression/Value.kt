package com.mlacker.micros.expression

class Value(private val value: Any = 1) : Expression {

    override val type = Type.String

    override fun eval(variables: Map<String, Any>) = value

    override fun toSql() = "$value"
}