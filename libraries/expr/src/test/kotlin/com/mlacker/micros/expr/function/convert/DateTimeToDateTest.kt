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

internal class DateTimeToDateTest {

    private val param0: Expression = mockk()
    private lateinit var dateTimeToDate: DateTimeToDate

    @BeforeEach
    fun setup() {
        every { param0.type } returns BasicType.DateTime
        dateTimeToDate = DateTimeToDate(listOf(param0))
    }

    @Test
    fun getType() {
        assertEquals(BasicType.Date, dateTimeToDate.type)
    }

    @Test
    fun eval() {
        every { param0.eval(any()) } returns LocalDateTime.of(2012, 12, 12, 12, 12, 12)
        assertEquals(LocalDate.of(2012, 12, 12), dateTimeToDate.eval(emptyMap()))
    }

    @Test
    fun testToString() {
        every { param0.toString() } returns "true"
        assertEquals("DateTimeToDate(true)", dateTimeToDate.toString())
    }
}