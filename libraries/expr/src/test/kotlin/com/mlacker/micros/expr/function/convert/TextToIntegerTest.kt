package com.mlacker.micros.expr.function.convert

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.types.BasicType
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class TextToIntegerTest {

    private val param0: Expression = mockk()
    private lateinit var textToInteger: TextToInteger

    @BeforeEach
    fun setup() {
        every { param0.type } returns BasicType.Text
        textToInteger = TextToInteger(listOf(param0))
    }

    @Test
    fun getType() {
        assertEquals(BasicType.Integer, textToInteger.type)
    }

    @Test
    fun eval() {
        every { param0.eval(any()) } returns "11"
        assertEquals(11L, textToInteger.eval(emptyMap()))
    }

    @Test
    fun testToString() {
        every { param0.toString() } returns "'1'"
        assertEquals("TextToInteger('1')", textToInteger.toString())
    }
}