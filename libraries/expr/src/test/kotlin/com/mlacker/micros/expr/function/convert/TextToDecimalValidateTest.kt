package com.mlacker.micros.expr.function.convert

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.types.BasicType
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class TextToDecimalValidateTest {

    private val param0: Expression = mockk()
    private lateinit var textToDecimalValidate: TextToDecimalValidate

    @BeforeEach
    fun setup() {
        every { param0.type } returns BasicType.Text
        textToDecimalValidate = TextToDecimalValidate(listOf(param0))
    }

    @Test
    fun getType() {
        assertEquals(BasicType.Boolean, textToDecimalValidate.type)
    }

    @Test
    fun eval() {
        every { param0.eval(any()) } returns "11.2"
        assertEquals(true, textToDecimalValidate.eval(emptyMap()))
        every { param0.eval(any()) } returns "-11.2"
        assertEquals(true, textToDecimalValidate.eval(emptyMap()))
        every { param0.eval(any()) } returns "11"
        assertEquals(true, textToDecimalValidate.eval(emptyMap()))
        every { param0.eval(any()) } returns "0"
        assertEquals(true, textToDecimalValidate.eval(emptyMap()))
        every { param0.eval(any()) } returns "QAQ"
        assertEquals(false, textToDecimalValidate.eval(emptyMap()))
        every { param0.eval(any()) } returns "--1"
        assertEquals(false, textToDecimalValidate.eval(emptyMap()))
        every { param0.eval(any()) } returns "1..2"
        assertEquals(false, textToDecimalValidate.eval(emptyMap()))
    }

    @Test
    fun testToString() {
        every { param0.toString() } returns "'1212'"
        assertEquals("TextToDecimalValidate('1212')", textToDecimalValidate.toString())
    }
}