package com.lacker.micros.expression.function.mathematical

import com.lacker.micros.expression.Expression
import com.lacker.micros.expression.Type
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class AbsTest {

    private val exp: Expression = mockk()
    private val abs: Abs = Abs(listOf(exp))

    @Test
    fun getType() {
        assertEquals(Type.Number, abs.type)
    }

    @Test
    fun eval() {
        every { exp.eval(any()) } returns 2
        assertEquals(2, abs.eval(emptyMap()))

        every { exp.eval(any()) } returns -32
        assertEquals(32, abs.eval(emptyMap()))
    }

    @Test
    fun toSql() {
        every { exp.toSql() } returns "-32"
        assertEquals("ABS(-32)", abs.toSql())
    }
}