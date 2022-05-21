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

class SecondTest {
    private val param0: Expression = mockk()
    private lateinit var second: Second

    @BeforeEach
    fun setup() {
        every { param0.type } returns BasicType.DateTime
        second = Second(listOf(param0))
    }

    @Test
    fun getType() {
        Assertions.assertEquals(BasicType.Integer, second.type)
    }

    @Test
    fun eval() {
        every { param0.eval(any()) } returns
                toDateTime("2021-03-07 14:15:16") andThen
                toDateTime("2020-05-11 17:18:19")
        Assertions.assertEquals(16L, second.eval(emptyMap()))
        Assertions.assertEquals(19L, second.eval(emptyMap()))
    }

    @Test
    fun testToString() {
        every { param0.toString() } returns "'2014-01-02 19:20:21'"
        Assertions.assertEquals("Second('2014-01-02 19:20:21')", second.toString())
    }

    private fun toDateTime(arg: String): LocalDateTime {
        return LocalDateTime.parse(arg, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
    }
}