package com.mlacker.micros.expr.function.mathematical

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.types.BasicType
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal

internal class FloorTest {

    private val param0: Expression = mockk()
    private val param1: Expression = mockk()
    private lateinit var floor: Floor

    @BeforeEach
    fun setup() {
        every { param0.type } returns BasicType.Decimal
        every { param1.type } returns BasicType.Integer
        floor = Floor(listOf(param0, param1))
    }

    @Test
    fun getType() {
        assertEquals(BasicType.Decimal, floor.type)
    }

    @Test
    fun eval() {
        every { param0.eval(any()) } returns BigDecimal("200.227")
        every { param1.eval(any()) } returns 2L
        assertEquals(BigDecimal("200.22"), floor.eval(emptyMap()))

        every { param0.eval(any()) } returns BigDecimal("200.99")
        every { param1.eval(any()) } returns 1L
        assertEquals(BigDecimal("200.9"), floor.eval(emptyMap()))
    }

    @Test
    fun testToString() {
        every { param0.toString() } returns "100.99"
        every { param1.toString() } returns "2"
        assertEquals("Floor(100.99, 2)", floor.toString())
    }
}