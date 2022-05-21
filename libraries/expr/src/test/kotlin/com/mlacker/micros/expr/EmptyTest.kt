package com.mlacker.micros.expr

import com.mlacker.micros.expr.types.BasicType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class EmptyTest {

    private val empty = Empty()

    @Test
    fun getType() {
        assertEquals(BasicType.Text, empty.type)
    }

    @Test
    fun eval() {
        assertEquals("", empty.eval(emptyMap()))
    }

    @Test
    fun testToString() {
        assertEquals("", empty.toString())
    }
}