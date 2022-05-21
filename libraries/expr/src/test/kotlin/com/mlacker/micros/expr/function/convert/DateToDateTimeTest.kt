package com.mlacker.micros.expr.function.convert

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.types.BasicType
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalDateTime

internal class DateToDateTimeTest {

    private val param0: Expression = mockk()
    private lateinit var dateToDateTime: DateToDateTime

    @BeforeEach
    fun setup() {
        every { param0.type } returns BasicType.Date
        dateToDateTime = DateToDateTime(listOf(param0))
    }

    @Test
    fun getType() {
        assertEquals(BasicType.DateTime, dateToDateTime.type)
    }

    @Test
    fun eval() {
        every { param0.eval(any()) } returns LocalDate.of(2012, 12, 12)
        assertEquals(LocalDateTime.of(2012, 12, 12, 0, 0, 0), dateToDateTime.eval(emptyMap()))
    }

    @Test
    fun testToString() {
        every { param0.toString() } returns "'2012-12-12'"
        assertEquals("DateToDateTime('2012-12-12')", dateToDateTime.toString())
    }
}