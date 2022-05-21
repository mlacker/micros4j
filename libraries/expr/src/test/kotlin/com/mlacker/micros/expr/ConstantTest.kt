package com.mlacker.micros.expr

import com.mlacker.micros.expr.types.BasicType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ConstantTest {

    @Test
    fun eval() {
        constants()
            .mapKeys { Constant(it.value, it.key) }
            .forEach { assertEquals(it.value, it.key.eval(emptyMap())) }
    }

    @Test
    fun testGenerate() {
        val datetimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        assertEquals(
            "'2012-12-10 10:30:59'",
            Constant(
                LocalDateTime.parse("2012-12-10 10:30:59", datetimeFormatter),
                BasicType.DateTime
            ).generate(emptyMap())
        )
        assertEquals(
            "'2020-01-01 10:00:00'",
            Constant(
                LocalDateTime.parse("2020-01-01 10:00:00", datetimeFormatter),
                BasicType.DateTime
            ).generate(emptyMap())
        )
    }

    @Test
    fun testToString() {
        assertEquals("true", Constant(true, BasicType.Boolean).toString())
        assertEquals("12.05", Constant(12.05, BasicType.Decimal).toString())
        assertEquals("\"foo\"", Constant("foo", BasicType.Text).toString())
        assertEquals("#1970-01-01#", Constant(LocalDate.EPOCH, BasicType.Date).toString())
        assertEquals(
            "#1970-01-01 00:00:00#", Constant(
                LocalDate.EPOCH.atStartOfDay(),
                BasicType.DateTime
            ).toString()
        )
    }

    @Test
    fun type() {
        constants()
            .mapValues { Constant(it.value, it.key) }
            .forEach { assertEquals(it.key, it.value.type) }
    }

    private fun constants() = mapOf(
        BasicType.Boolean to true,
        BasicType.Decimal to 12.05,
        BasicType.Text to "foo"
    )
}
