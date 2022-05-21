package com.mlacker.micros.expr.function.mathematical

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.types.BasicType
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class AbsTest {

    private val param0: Expression = mockk()
    private lateinit var abs: Abs

    @BeforeEach
    fun setup() {
        every { param0.type } returns BasicType.Decimal
        abs = Abs(listOf(param0))
    }

    @Test
    fun getType() {
        assertEquals(BasicType.Decimal, abs.type)
    }

    @Test
    fun eval() {
        every { param0.eval(any()) } returns 2L
        assertEquals(2L, abs.eval(emptyMap()))

        every { param0.eval(any()) } returns -32L
        assertEquals(32L, abs.eval(emptyMap()))
    }

    @Test
    fun testToString() {
        every { param0.toString() } returns "-32"
        assertEquals("Abs(-32)", abs.toString())
    }
}