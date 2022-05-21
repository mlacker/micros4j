package com.mlacker.micros.expr.function.convert

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.types.BasicType
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class TextToIntegerValidateTest {

    private val param0: Expression = mockk()
    private lateinit var textToIntegerValidate: TextToIntegerValidate

    @BeforeEach
    fun setup() {
        every { param0.type } returns BasicType.Text
        textToIntegerValidate = TextToIntegerValidate(listOf(param0))
    }

    @Test
    fun getType() {
        assertEquals(BasicType.Boolean, textToIntegerValidate.type)
    }

    @Test
    fun eval() {
        every { param0.eval(any()) } returns "11"
        assertEquals(true, textToIntegerValidate.eval(emptyMap()))

        every { param0.eval(any()) } returns "11.2"
        assertEquals(false, textToIntegerValidate.eval(emptyMap()))
    }

    @Test
    fun testToString() {
        every { param0.toString() } returns "'1'"
        assertEquals("TextToIntegerValidate('1')", textToIntegerValidate.toString())
    }
}