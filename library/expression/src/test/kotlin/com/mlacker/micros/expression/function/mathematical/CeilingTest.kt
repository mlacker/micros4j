package com.mlacker.micros.expression.function.mathematical

import com.mlacker.micros.expression.Expression
import com.mlacker.micros.expression.Type
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class CeilingTest {

    private val exp: Expression = mockk()
    private val ceiling: Ceiling = Ceiling(listOf(exp))

    @Test
    fun getType() {
        assertEquals(Type.Number, ceiling.type)
    }

    @Test
    fun eval() {
        every { exp.eval(any()) } returns 1.23
        assertEquals(2.0, ceiling.eval(emptyMap()))

        every { exp.eval(any()) } returns -1.23
        assertEquals(-1.0, ceiling.eval(emptyMap()))

        every { exp.eval(any()) } returns 1
        assertEquals(1.0, ceiling.eval(emptyMap()))
    }

    @Test
    fun toSql() {
        every { exp.toSql() } returns "1.23"
        assertEquals("CEILING(1.23)", ceiling.toSql())
    }
}