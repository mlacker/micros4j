package com.mlacker.micros.expr.function.datetime

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.types.BasicType
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DateTimeDiffTest {
    private val param0: Expression = mockk()
    private val param1: Expression = mockk()
    private val param2: Expression = mockk()
    private lateinit var dateTimeDiff: DateTimeDiff

    @BeforeEach
    fun setup() {
        every { param0.type } returns BasicType.DateTime
        every { param1.type } returns BasicType.DateTime
        every { param2.type } returns BasicType.Text

        dateTimeDiff = DateTimeDiff(listOf(param0, param1, param2))
    }

    @Test
    fun getType() {

        Assertions.assertEquals(BasicType.Integer, dateTimeDiff.type)
    }

    @Test
    fun eval() {
        every { param0.eval(any()) } returns
                toDateTime("2014-01-02 00:00:00") andThen
                toDateTime("2015-01-02 00:00:00") andThen
                toDateTime("2014-01-02 00:00:00") andThen
                toDateTime("2014-01-02 00:00:00") andThen
                toDateTime("2014-01-02 14:01:01") andThen
                toDateTime("2014-01-02 14:01:01") andThen
                toDateTime("2014-01-02 14:01:01")

        every { param1.eval(any()) } returns
                toDateTime("2015-01-02 00:00:00") andThen
                toDateTime("2014-01-02 00:00:00") andThen
                toDateTime("2014-03-05 00:00:00") andThen
                toDateTime("2014-01-05 00:00:00") andThen
                toDateTime("2014-01-02 18:01:01") andThen
                toDateTime("2014-01-02 14:55:01") andThen
                toDateTime("2014-01-02 14:01:31")

        every { param2.eval(any()) } returns
                "y" andThen
                "y" andThen
                "mo" andThen
                "d" andThen
                "h" andThen
                "mi" andThen
                "s"

        Assertions.assertEquals(1L, dateTimeDiff.eval(emptyMap()))
        Assertions.assertEquals(-1L, dateTimeDiff.eval(emptyMap()))
        Assertions.assertEquals(2L, dateTimeDiff.eval(emptyMap()))
        Assertions.assertEquals(3L, dateTimeDiff.eval(emptyMap()))
        Assertions.assertEquals(4L, dateTimeDiff.eval(emptyMap()))
        Assertions.assertEquals(54L, dateTimeDiff.eval(emptyMap()))
        Assertions.assertEquals(30L, dateTimeDiff.eval(emptyMap()))
    }

    @Test
    fun testToString() {
        every { param0.toString() } returns "'2014-01-02 00:00:00'"
        every { param1.toString() } returns "'2014-02-02 00:00:00'"
        every { param2.toString() } returns "'y'"
        Assertions.assertEquals("DateTimeDiff('2014-01-02 00:00:00', '2014-02-02 00:00:00', 'y')", dateTimeDiff.toString())
    }

    private fun toDateTime(arg: String): LocalDateTime {
        return LocalDateTime.parse(arg, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
    }
}