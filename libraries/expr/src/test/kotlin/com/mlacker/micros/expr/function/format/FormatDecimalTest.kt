package com.mlacker.micros.expr.function.format

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.types.BasicType
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class FormatDecimalTest {
    private val param0: Expression = mockk()
    private val param1: Expression = mockk()
    private val param2: Expression = mockk()
    private lateinit var formatDecimal: FormatDecimal

    @BeforeEach
    fun setup() {
        every { param0.type } returns BasicType.Decimal
        every { param1.type } returns BasicType.Integer
        every { param2.type } returns BasicType.Boolean
        formatDecimal = FormatDecimal(listOf(param0, param1, param2))
    }

    @Test
    fun getType() {
        Assertions.assertEquals(BasicType.Text, formatDecimal.type)
    }

    @Test
    fun eval() {
        every { param0.eval(any()) } returns BigDecimal(1234.567) andThen BigDecimal(1234.5678)
        every { param1.eval(any()) } returns 2L andThen 3L
        every { param2.eval(any()) } returns true andThen false
        Assertions.assertEquals("1,234.57", formatDecimal.eval(emptyMap()))
        Assertions.assertEquals("1234.568", formatDecimal.eval(emptyMap()))
    }

    @Test
    fun testToString() {
        every { param0.toString() } returns "'123.45'"
        every { param1.toString() } returns "2"
        every { param2.toString() } returns "true"
        Assertions.assertEquals("FormatDecimal('123.45', 2, true)", formatDecimal.toString())
    }
}