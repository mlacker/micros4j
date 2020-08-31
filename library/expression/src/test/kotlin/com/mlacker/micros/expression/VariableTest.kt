package com.mlacker.micros.expression

import com.mlacker.micros.expression.core.Variable
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

class VariableTest {

    private val variable = Variable("foo", Type.Number)

    @Test
    fun `key is empty then throws`() {
        assertThrows<IllegalArgumentException> {
            Variable("", Type.Number)
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
        assertEquals(100, variable.eval(mapOf("foo" to 100)))
    }

    @Test
    fun `toSql not implemented`() {
        assertThrows<NotImplementedError> {
            variable.toSql()
        }
    }
}