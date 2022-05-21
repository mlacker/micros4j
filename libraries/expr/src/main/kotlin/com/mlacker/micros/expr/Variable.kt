package com.mlacker.micros.expr

class Variable(val key: String, override val type: Type) : Expression {

    init {
        if (key.isBlank()) throw IllegalArgumentException("key cannot be null")
    }

    override fun eval(variables: Map<String, Any>): Any? {
        if (variables.containsKey(key)) {
            return variables[key]
        } else {
            throw IllegalArgumentException("variable must be exists")
        }
    }

    override fun generate(variables: Map<String, Any>): String {
        return key
    }

    override fun toString(): String = key
}
