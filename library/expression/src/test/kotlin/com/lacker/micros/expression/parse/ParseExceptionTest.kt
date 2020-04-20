package com.lacker.micros.expression.parse

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class ParseExceptionTest {

    @Test
    fun getMessage() {
        val exception = ParseException("'foo'bar", 5)

        val expected = StringBuilder()
                .appendln("'foo'bar")
                .appendln("     ↑ (pos 5)")
                .toString()

        assertEquals(expected, exception.message)
    }
}