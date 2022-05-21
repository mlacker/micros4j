package com.mlacker.micros.expr.function.format

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.types.BasicType
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class FormatDateTimeTest {
    private val param0: Expression = mockk()
    private val param1: Expression = mockk()
    private lateinit var formatDateTime: FormatDateTime

    @BeforeEach
    fun setup() {
        every { param0.type } returns BasicType.DateTime
        every { param1.type } returns BasicType.Text
        formatDateTime = FormatDateTime(listOf(param0, param1))
    }

    @Test
    fun getType() {
        Assertions.assertEquals(BasicType.Text, formatDateTime.type)
    }

    @Test
    fun eval() {
        every { param0.eval(any()) } returns toDateTime("2014-01-02 15:23:45") andThen toDateTime("2014-01-02 15:23:45")
        every { param1.eval(any()) } returns "yyyy年MM月dd日" andThen "HH点mm分ss秒"
        Assertions.assertEquals("2014年01月02日", formatDateTime.eval(emptyMap()))
        Assertions.assertEquals("15点23分45秒", formatDateTime.eval(emptyMap()))
    }

    @Test
    fun testToString() {
        every { param0.toString() } returns "'2014-01-02 15:23:45'"
        every { param1.toString() } returns "'yyyy年MM月dd日'"
        Assertions.assertEquals("FormatDateTime('2014-01-02 15:23:45', 'yyyy年MM月dd日')", formatDateTime.toString())
    }

    fun toDateTime(arg: String): LocalDateTime {
        return LocalDateTime.parse(arg, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
    }
}