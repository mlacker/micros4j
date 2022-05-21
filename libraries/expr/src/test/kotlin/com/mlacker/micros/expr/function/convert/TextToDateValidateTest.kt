package com.mlacker.micros.expr.function.convert

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.types.BasicType
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class TextToDateValidateTest {

    private val param0: Expression = mockk()
    private lateinit var textToDateValidate: TextToDateValidate

    @BeforeEach
    fun setup() {
        every { param0.type } returns BasicType.Text
        textToDateValidate = TextToDateValidate(listOf(param0))
    }

    @Test
    fun getType() {
        assertEquals(BasicType.Boolean, textToDateValidate.type)
    }

    @Test
    fun eval() {
        every { param0.eval(any()) } returns "2012-12-12"
        assertEquals(true, textToDateValidate.eval(emptyMap()))

        every { param0.eval(any()) } returns "2012-12-12 12:12:12"
        assertEquals(false, textToDateValidate.eval(emptyMap()))
    }

    @Test
    fun testToString() {
        every { param0.toString() } returns "'2012-12-12'"
        assertEquals("TextToDateValidate('2012-12-12')", textToDateValidate.toString())
    }
}