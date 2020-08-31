package com.mlacker.micros.expression.parse

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ParseExceptionTest {

    @Test
    fun getMessage() {
        val exception = ParseException("'foo'bar", 5)

        val expected = StringBuilder()
                .appendLine("'foo'bar")
                .appendLine("     ↑ (pos 5)")
                .toString()

        assertEquals(expected, exception.message)
    }
}