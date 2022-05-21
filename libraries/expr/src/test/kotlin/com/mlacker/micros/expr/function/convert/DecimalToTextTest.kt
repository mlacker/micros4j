package com.mlacker.micros.expr.function.convert

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.types.BasicType
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal

internal class DecimalToTextTest {

    private val param0: Expression = mockk()
    private lateinit var decimalToText: DecimalToText

    @BeforeEach
    fun setup() {
        every { param0.type } returns BasicType.Decimal
        decimalToText = DecimalToText(listOf(param0))
    }

    @Test
    fun getType() {
        assertEquals(BasicType.Text, decimalToText.type)
    }

    @Test
    fun eval() {
        every { param0.eval(any()) } returns BigDecimal.ZERO
        assertEquals("0", decimalToText.eval(emptyMap()))

        every { param0.eval(any()) } returns BigDecimal("-1")
        assertEquals("-1", decimalToText.eval(emptyMap()))

        every { param0.eval(any()) } returns BigDecimal("1")
        assertEquals("1", decimalToText.eval(emptyMap()))
    }

    @Test
    fun testToString() {
        every { param0.toString() } returns "1.2"
        assertEquals("DecimalToText(1.2)", decimalToText.toString())
    }
}