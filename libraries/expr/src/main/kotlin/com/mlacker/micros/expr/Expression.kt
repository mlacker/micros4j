package com.mlacker.micros.expr

interface Expression {

    val type: Type

    fun eval(variables: Map<String, Any>): Any?

    fun generate(variables: Map<String, Any>): String {
        throw UnsupportedOperationException("$this unsupported in sql")
    }
}
