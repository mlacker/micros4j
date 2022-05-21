package com.mlacker.micros.expr.function.convert

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.types.BasicType
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal

internal class TextToDecimalTest {

    private val param0: Expression = mockk()
    private lateinit var textToDecimal: TextToDecimal

    @BeforeEach
    fun setup() {
        every { param0.type } returns BasicType.Text
        textToDecimal = TextToDecimal(listOf(param0))
    }

    @Test
    fun getType() {
        assertEquals(BasicType.Decimal, textToDecimal.type)
    }

    @Test
    fun eval() {
        every { param0.eval(any()) } returns "11"
        assertEquals(11L, textToDecimal.eval(emptyMap()))

        every { param0.eval(any()) } returns "22.2"
        assertEquals(BigDecimal("22.2"), textToDecimal.eval(emptyMap()))
    }

    @Test
    fun testToString() {
        every { param0.toString() } returns "'2012-12-12'"
        assertEquals("TextToDecimal('2012-12-12')", textToDecimal.toString())
    }
}