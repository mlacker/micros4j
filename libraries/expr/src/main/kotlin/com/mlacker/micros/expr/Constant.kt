package com.mlacker.micros.expr

import com.mlacker.micros.expr.types.BasicType
import java.time.LocalDateTime

class Constant(internal val value: Any, override val type: Type) :
    Expression {

    override fun eval(variables: Map<String, Any>): Any? = value

    override fun generate(variables: Map<String, Any>): String {
        return when (type) {
            BasicType.Any,
            BasicType.Boolean,
            BasicType.Integer,
            BasicType.Decimal,
            BasicType.Unknown -> value.toString()
            BasicType.Date,
            BasicType.Text -> "'$value'"
            BasicType.DateTime -> "'${(value as LocalDateTime).toChinaString()}'"
            else -> TODO()
        }
    }

    override fun toString(): String {
        return when (type) {
            BasicType.Boolean,
            BasicType.Integer,
            BasicType.Decimal,
            BasicType.Any,
            BasicType.Unknown -> value.toString()
            BasicType.Text -> "\"$value\""
            BasicType.Date -> "#$value#"
            BasicType.DateTime -> "#${(value as LocalDateTime).toChinaString()}#"
            else -> TODO()
        }
    }


}
