package com.mlacker.micros.expr.function.convert

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.types.BasicType
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDate

internal class TextToDateTest {

    private val param0: Expression = mockk()
    private lateinit var textToDate: TextToDate

    @BeforeEach
    fun setup() {
        every { param0.type } returns BasicType.Text
        textToDate = TextToDate(listOf(param0))
    }

    @Test
    fun getType() {
        assertEquals(BasicType.Date, textToDate.type)
    }

    @Test
    fun eval() {
        every { param0.eval(any()) } returns "2012-12-12"
        assertEquals(LocalDate.of(2012, 12, 12), textToDate.eval(emptyMap()))
    }

    @Test
    fun testToString() {
        every { param0.toString() } returns "'2012-12-12'"
        assertEquals("TextToDate('2012-12-12')", textToDate.toString())
    }
}