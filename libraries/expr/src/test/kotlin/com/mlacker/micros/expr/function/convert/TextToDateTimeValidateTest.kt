package com.mlacker.micros.expr.function.convert

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.types.BasicType
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class TextToDateTimeValidateTest {

    private val param0: Expression = mockk()
    private lateinit var textToDateTimeValidate: TextToDateTimeValidate

    @BeforeEach
    fun setup() {
        every { param0.type } returns BasicType.Text
        textToDateTimeValidate = TextToDateTimeValidate(listOf(param0))
    }

    @Test
    fun getType() {
        assertEquals(BasicType.Boolean, textToDateTimeValidate.type)
    }

    @Test
    fun eval() {
        every { param0.eval(any()) } returns "2012-12-12 12:12:12"
        assertEquals(true, textToDateTimeValidate.eval(emptyMap()))

        every { param0.eval(any()) } returns "2012-12-1212312312"
        assertEquals(false, textToDateTimeValidate.eval(emptyMap()))
    }

    @Test
    fun testToString() {
        every { param0.toString() } returns "'2012-12-12 12:12:12'"
        assertEquals("TextToDateTimeValidate('2012-12-12 12:12:12')", textToDateTimeValidate.toString())
    }
}