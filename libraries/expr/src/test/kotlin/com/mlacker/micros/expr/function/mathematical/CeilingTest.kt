package com.mlacker.micros.expr.function.mathematical

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.types.BasicType
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal

internal class CeilingTest {

    private val param0: Expression = mockk()
    private val param1: Expression = mockk()
    private lateinit var ceiling: Ceiling

    @BeforeEach
    fun setup() {
        every { param0.type } returns BasicType.Decimal
        every { param1.type } returns BasicType.Integer
        ceiling = Ceiling(listOf(param0, param1))
    }

    @Test
    fun getType() {
        assertEquals(BasicType.Decimal, ceiling.type)
    }

    @Test
    fun eval() {
        every { param0.eval(any()) } returns BigDecimal("200.227")
        every { param1.eval(any()) } returns 2L
        assertEquals(BigDecimal("200.23"), ceiling.eval(emptyMap()))

        every { param0.eval(any()) } returns BigDecimal(200L)
        every { param1.eval(any()) } returns 2L
        assertEquals(BigDecimal("200.00"), ceiling.eval(emptyMap()))

        every { param0.eval(any()) } returns BigDecimal("200.221")
        every { param1.eval(any()) } returns 2L
        assertEquals(BigDecimal("200.23"), ceiling.eval(emptyMap()))
    }


    @Test
    fun testToString() {
        every { param0.toString() } returns "1.236"
        every { param1.toString() } returns "2"
        assertEquals("Ceiling(1.236, 2)", ceiling.toString())
    }
}