package com.mlacker.micros.expr.function.convert

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.types.BasicType
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal

internal class DecimalToIntegerTest {

    private val param0: Expression = mockk()
    private lateinit var decimalToInteger: DecimalToInteger

    @BeforeEach
    fun setup() {
        every { param0.type } returns BasicType.Decimal
        decimalToInteger = DecimalToInteger(listOf(param0))
    }

    @Test
    fun getType() {
        assertEquals(BasicType.Integer, decimalToInteger.type)
    }

    @Test
    fun eval() {
        every { param0.eval(any()) } returns BigDecimal("12")
        assertEquals(12L, decimalToInteger.eval(emptyMap()))

        every { param0.eval(any()) } returns BigDecimal("12.22")
        assertEquals(12L, decimalToInteger.eval(emptyMap()))
    }

    @Test
    fun testToString() {
        every { param0.toString() } returns "1.9"
        assertEquals("DecimalToInteger(1.9)", decimalToInteger.toString())
    }
}