package com.mlacker.micros.expr.function.mathematical

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.types.BasicType
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal

internal class PowerTest {

    private val param0: Expression = mockk()
    private val param1: Expression = mockk()
    private lateinit var power: Power

    @BeforeEach
    fun setup() {
        every { param0.type } returns BasicType.Decimal
        every { param1.type } returns BasicType.Integer
        power = Power(listOf(param0, param1))
    }

    @Test
    fun getType() {
        assertEquals(BasicType.Decimal, power.type)
    }

    @Test
    fun eval() {
        every { param0.eval(any()) } returns BigDecimal("2.3")
        every { param1.eval(any()) } returns 2L
        assertEquals(BigDecimal("5.29"), power.eval(emptyMap()))

        every { param0.eval(any()) } returns BigDecimal(-32L)
        every { param1.eval(any()) } returns 2L
        assertEquals(1024L, power.eval(emptyMap()))

        every { param0.eval(any()) } returns BigDecimal("-99")
        every { param1.eval(any()) } returns 0L
        assertEquals(1L, power.eval(emptyMap()))
    }

    @Test
    fun testToString() {
        every { param0.toString() } returns "-32"
        every { param1.toString() } returns "66"
        assertEquals("Power(-32, 66)", power.toString())
    }
}