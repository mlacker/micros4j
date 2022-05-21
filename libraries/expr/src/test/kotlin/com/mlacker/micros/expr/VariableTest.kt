package com.mlacker.micros.expr

import com.mlacker.micros.expr.types.BasicType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class VariableTest {

    private val variable = Variable("Foo", BasicType.Unknown)

    @Test
    fun `key is empty then throws`() {
        assertThrows<IllegalArgumentException> {
            Variable("", BasicType.Unknown)
        }
    }

    @Test
    fun `eval variable not exists then throws`() {
        assertThrows<IllegalArgumentException> {
            variable.eval(emptyMap())
        }
    }

    @Test
    fun eval() {
        assertEquals(100, variable.eval(mapOf("Foo" to 100)))
    }

    @Test
    fun testToString() {
        assertEquals("Foo", variable.toString())
    }
}