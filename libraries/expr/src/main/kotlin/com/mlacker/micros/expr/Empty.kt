package com.mlacker.micros.expr

import com.mlacker.micros.expr.types.BasicType

class Empty : Expression {

    override val type = BasicType.Text

    override fun eval(variables: Map<String, Any>): Any? = ""
    override fun generate(variables: Map<String, Any>): String = ""

    override fun toString(): String = ""
}
