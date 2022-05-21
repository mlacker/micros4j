package com.mlacker.micros.expr.function.format

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.types.BasicType
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class FormatPercentTest {
    private val param0: Expression = mockk()
    private val param1: Expression = mockk()
    private lateinit var formatPercent: FormatPercent

    @BeforeEach
    fun setup() {
        every { param0.type } returns BasicType.Decimal
        every { param1.type } returns BasicType.Integer
        formatPercent = FormatPercent(listOf(param0, param1))
    }

    @Test
    fun getType() {
        Assertions.assertEquals(BasicType.Text, formatPercent.type)
    }

    @Test
    fun eval() {
        every { param0.eval(any()) } returns BigDecimal(0.12345) andThen BigDecimal(1.25678)
        every { param1.eval(any()) } returns 1L andThen 2L
        Assertions.assertEquals("12.3%", formatPercent.eval(emptyMap()))
        Assertions.assertEquals("125.68%", formatPercent.eval(emptyMap()))
    }

    @Test
    fun testToString() {
        every { param0.toString() } returns "'123.45'"
        every { param1.toString() } returns "2"
        Assertions.assertEquals("FormatPercent('123.45', 2)", formatPercent.toString())
    }
}