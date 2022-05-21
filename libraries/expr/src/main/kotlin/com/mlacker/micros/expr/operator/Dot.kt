package com.mlacker.micros.expr.operator

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.Type
import com.mlacker.micros.expr.Variable

class Dot(internal val operand: Expression, internal val property: Variable) : Expression {

    override val type: Type = property.type

    override fun eval(variables: Map<String, Any>): Any? {
        val instance = operand.eval(variables) ?: return null

        if (instance is Map<*, *>) {
            return instance[property.key]
        }

        try {
            val method = instance.javaClass.declaredMethods.find { it.name == "get$camelName" }
            return if (method != null) {
                method.invoke(instance)
            } else {
                instance.javaClass.getDeclaredField(property.key).get(instance)
            }
        } catch (ex: NoSuchFieldException) {
            throw NoSuchElementException("cannot find property $property in $operand")
        }
    }

    fun setValue(variables: Map<String, Any>, value: Any?) {
        val instance = operand.eval(variables) ?: throw NullPointerException("instance is null")

        if (instance is MutableMap<*, *>) {
            (instance as MutableMap<String, Any?>)[property.key] = value
        } else {
            try {
                instance.javaClass
                    .declaredMethods.find { it.name == "set$camelName" }
                    ?.also { it.invoke(instance, value) }
                    ?: instance.javaClass.getDeclaredField(property.key).set(instance, value)
            } catch (ex: NoSuchFieldException) {
                throw NoSuchElementException("cannot find property $property in $operand")
            }
        }
    }

    override fun generate(variables: Map<String, Any>): String {
        return "${operand.generate(variables)}.${property.generate(variables)}"
    }

    private val camelName = property.key.first().uppercaseChar() + property.key.substring(1)

    override fun toString(): String = "$operand.$property"
}
