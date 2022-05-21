package com.mlacker.micros.expr.parse

import com.mlacker.micros.expr.Variable
import com.mlacker.micros.expr.types.BasicType
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime

class OperandCreatorTest {

    private val creator = OperandCreator()
    private val variables = emptyMap<String, Any>()

    @Test
    fun integer() {
        assertExpression("1", BasicType.Integer, 1L)
        assertExpression("-123", BasicType.Integer, -123L)
    }

    @Test
    fun decimal() {
        assertExpression("3.14", BasicType.Decimal, BigDecimal("3.14"))
    }

    @Test
    fun boolean() {
        assertExpression("true", BasicType.Boolean, true)
        assertExpression("false", BasicType.Boolean, false)
    }

    @Test
    fun date() {
        assertExpression("#1970-01-01#", BasicType.Date, LocalDate.parse("1970-01-01"))
    }

    @Test
    fun datetime() {
        assertExpression("#1970-01-01 00:00:00#", BasicType.DateTime, LocalDateTime.parse("1970-01-01T00:00:00"))
    }

    @Test
    fun text() {
        assertExpression("\"Foo\"", BasicType.Text, "Foo")
    }

    @Test
    fun variable() {
        val expr = creator.create("Foo")

        if (expr is Variable) {
            assertEquals("Foo", expr.key)
            assertEquals(BasicType.Unknown, expr.type)
        } else {
            fail()
        }
    }

    @Test
    fun `raise format error`() {
        assertThrows(IllegalArgumentException::class.java) { creator.create("1d3") }
    }

    private fun assertExpression(expression: String, type: BasicType, expectedValue: Any) {
        val expr = creator.create(expression)

        assertEquals(type, expr.type)
        assertEquals(expectedValue, expr.eval(variables))
    }
}