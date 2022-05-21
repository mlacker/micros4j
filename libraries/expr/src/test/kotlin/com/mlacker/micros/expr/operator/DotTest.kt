package com.mlacker.micros.expr.operator

import com.mlacker.micros.expr.Variable
import com.mlacker.micros.expr.types.BasicType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class DotTest {

    private val expression: Dot = Dot(Variable("Foo", BasicType.Unknown), Variable("Bar", BasicType.Text))

    @Test
    fun getType() {
        assertEquals(BasicType.Text, expression.type)
    }

    @Test
    fun eval() {
        val context = mapOf("Foo" to Foo("Test"))

        assertEquals("Test", expression.eval(context))
    }

    @Test
    fun setValue() {
        val context = mapOf("Foo" to Foo("Test"))

        expression.setValue(context, "Changed")

        assertEquals("Changed", context["Foo"]?.Bar)
    }

    @Test
    fun testToString() {
        assertEquals("Foo.Bar", expression.toString())
    }

    @Test
    fun testGenerate() {
        assertEquals("Foo.Bar", expression.generate(emptyMap()))
    }

    class Foo(var Bar: String)
}
