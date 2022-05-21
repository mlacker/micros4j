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

class NewDateTimeTest {
    private val param0: Expression = mockk()
    private val param1: Expression = mockk()
    private val param2: Expression = mockk()
    private val param3: Expression = mockk()
    private val param4: Expression = mockk()
    private val param5: Expression = mockk()
    private lateinit var newDateTime: NewDateTime

    @BeforeEach
    fun setup() {
        every { param0.type } returns BasicType.Integer
        every { param1.type } returns BasicType.Integer
        every { param2.type } returns BasicType.Integer
        every { param3.type } returns BasicType.Integer
        every { param4.type } returns BasicType.Integer
        every { param5.type } returns BasicType.Integer

        newDateTime = NewDateTime(listOf(param0, param1, param2, param3, param4, param5))
    }

    @Test
    fun getType() {

        Assertions.assertEquals(BasicType.DateTime, newDateTime.type)
    }

    @Test
    fun eval() {
        every { param0.eval(any()) } returns 2014L
        every { param1.eval(any()) } returns 1L
        every { param2.eval(any()) } returns 2L
        every { param3.eval(any()) } returns 4L
        every { param4.eval(any()) } returns 5L
        every { param5.eval(any()) } returns 6L
        Assertions.assertEquals(toDateTime("2014-01-02 04:05:06"), newDateTime.eval(emptyMap()))
    }

    @Test
    fun testToString() {
        every { param0.toString() } returns "2014"
        every { param1.toString() } returns "1"
        every { param2.toString() } returns "2"
        every { param3.toString() } returns "3"
        every { param4.toString() } returns "4"
        every { param5.toString() } returns "5"
        Assertions.assertEquals("NewDateTime(2014, 1, 2, 3, 4, 5)", newDateTime.toString())
    }

    private fun toDateTime(arg: String): LocalDateTime {
        return LocalDateTime.parse(arg, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
    }
}