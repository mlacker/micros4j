package com.mlacker.micros.expr.function.datetime

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.types.BasicType
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DateTimeAddTest {
    private val param0: Expression = mockk()
    private val param1: Expression = mockk()
    private val param2: Expression = mockk()
    private lateinit var dateTimeAdd: DateTimeAdd

    @BeforeEach
    fun setup() {
        every { param0.type } returns BasicType.DateTime
        every { param1.type } returns BasicType.Integer
        every { param2.type } returns BasicType.Text
        dateTimeAdd = DateTimeAdd(listOf(param0, param1, param2))
    }

    @Test
    fun getType() {
        Assertions.assertEquals(BasicType.DateTime, dateTimeAdd.type)
    }

    @Test
    fun eval() {

        every { param0.eval(any()) } returns
                toDateTime("2014-01-02 00:00:00") andThen
                toDateTime("2014-01-02 00:00:00") andThen
                toDateTime("2014-01-02 00:00:00") andThen
                toDateTime("2014-01-02 00:00:00") andThen
                toDateTime("2014-01-02 14:01:01") andThen
                toDateTime("2014-01-02 14:01:01") andThen
                toDateTime("2014-01-02 14:01:01") andThen
                toDate("2014-01-02")

        every { param1.eval(any()) } returns
                1L andThen
                -1L andThen
                1L andThen
                1L andThen
                3L andThen
                30L andThen
                30L andThen
                30L

        every { param2.eval(any()) } returns
                "y" andThen
                "y" andThen
                "mo" andThen
                "d" andThen
                "h" andThen
                "mi" andThen
                "s" andThen
                "s"

        Assertions.assertEquals(toDateTime("2015-01-02 00:00:00"), dateTimeAdd.eval(emptyMap()))
        Assertions.assertEquals(toDateTime("2013-01-02 00:00:00"), dateTimeAdd.eval(emptyMap()))
        Assertions.assertEquals(toDateTime("2014-02-02 00:00:00"), dateTimeAdd.eval(emptyMap()))
        Assertions.assertEquals(toDateTime("2014-01-03 00:00:00"), dateTimeAdd.eval(emptyMap()))
        Assertions.assertEquals(toDateTime("2014-01-02 17:01:01"), dateTimeAdd.eval(emptyMap()))
        Assertions.assertEquals(toDateTime("2014-01-02 14:31:01"), dateTimeAdd.eval(emptyMap()))
        Assertions.assertEquals(toDateTime("2014-01-02 14:01:31"), dateTimeAdd.eval(emptyMap()))
        Assertions.assertEquals(toDateTime("2014-01-02 00:00:30"), dateTimeAdd.eval(emptyMap()))
    }

    @Test
    fun testToString() {
        every { param0.toString() } returns "'2014-01-02 00:00:00'"
        every { param1.toString() } returns "1"
        every { param2.toString() } returns "'y'"
        Assertions.assertEquals("DateTimeAdd('2014-01-02 00:00:00', 1, 'y')", dateTimeAdd.toString())
    }

    private fun toDateTime(arg: String): LocalDateTime {
        return LocalDateTime.parse(arg, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
    }

    private fun toDate(arg: String): LocalDate {
        return LocalDate.parse(arg, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    }
}