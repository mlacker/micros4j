package com.mlacker.micros.expr.function.mathematical

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.types.BasicType
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal

internal class MaxTest {

    private val param0: Expression = mockk()
    private val param1: Expression = mockk()
    private lateinit var max: Max

    @BeforeEach
    fun setup() {
        every { param0.type } returns BasicType.Decimal
        every { param1.type } returns BasicType.Integer
        max = Max(listOf(param0, param1))
    }

    @Test
    fun getType() {
        assertEquals(BasicType.Any, max.type)
    }

    @Test
    fun eval() {
        every { param0.eval(any()) } returns 2L
        every { param1.eval(any()) } returns BigDecimal("99")
        assertEquals(99L, max.eval(emptyMap()))

        every { param0.eval(any()) } returns -32L
        every { param1.eval(any()) } returns 99L
        assertEquals(99L, max.eval(emptyMap()))

        every { param0.eval(any()) } returns BigDecimal("-99")
        every { param1.eval(any()) } returns -1L
        assertEquals(-1L, max.eval(emptyMap()))
    }

    @Test
    fun testToString() {
        every { param0.toString() } returns "-32"
        every { param1.toString() } returns "66"
        assertEquals("Max(-32, 66)", max.toString())
    }
}
