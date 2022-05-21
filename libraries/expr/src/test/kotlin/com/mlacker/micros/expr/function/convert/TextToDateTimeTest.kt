package com.mlacker.micros.expr.function.convert

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.types.BasicType
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

internal class TextToDateTimeTest {

    private val param0: Expression = mockk()
    private lateinit var textToDateTime: TextToDateTime

    @BeforeEach
    fun setup() {
        every { param0.type } returns BasicType.Text
        textToDateTime = TextToDateTime(listOf(param0))
    }

    @Test
    fun getType() {
        assertEquals(BasicType.DateTime, textToDateTime.type)
    }

    @Test
    fun eval() {
        every { param0.eval(any()) } returns "2012-12-12 12:12:12"
        assertEquals(LocalDateTime.of(2012, 12, 12, 12, 12, 12), textToDateTime.eval(emptyMap()))
    }

    @Test
    fun testToString() {
        every { param0.toString() } returns "'2012-12-12 12:12:12'"
        assertEquals("TextToDateTime('2012-12-12 12:12:12')", textToDateTime.toString())
    }
}