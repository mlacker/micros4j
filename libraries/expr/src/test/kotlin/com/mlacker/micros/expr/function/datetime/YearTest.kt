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

class YearTest {
    private val param0: Expression = mockk()
    private lateinit var year: Year

    @BeforeEach
    fun setup() {
        every { param0.type } returns BasicType.DateTime
        year = Year(listOf(param0))
    }

    @Test
    fun getType() {
        Assertions.assertEquals(BasicType.Integer, year.type)
    }

    @Test
    fun eval() {
        every { param0.eval(any()) } returns
                toDateTime("2021-03-07 00:00:00") andThen
                toDateTime("2020-05-11 00:00:00")
        Assertions.assertEquals(2021L, year.eval(emptyMap()))
        Assertions.assertEquals(2020L, year.eval(emptyMap()))
    }

    @Test
    fun testToString() {
        every { param0.toString() } returns "'2014-01-02 00:00:00'"
        Assertions.assertEquals("Year('2014-01-02 00:00:00')", year.toString())
    }

    private fun toDateTime(arg: String): LocalDateTime {
        return LocalDateTime.parse(arg, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
    }
}