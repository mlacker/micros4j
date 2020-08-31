package com.mlacker.micros.expression.core

import com.mlacker.micros.expression.Expression
import com.mlacker.micros.expression.Type

class Constant(private val value: Any, override val type: Type) : Expression {

    override fun eval(variables: Map<String, Any>): Any = value

    override fun toSql(): String = when (type) {
        Type.Boolean -> if (value as Boolean) "1" else "0"
        Type.Number -> value.toString()
        Type.String -> "'$value'"
        Type.Date -> TODO("not implemented")
        Type.Array -> TODO("not implemented")
    }
}