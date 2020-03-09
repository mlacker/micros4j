package com.lacker.micros.expression.function.mathematical

import com.lacker.micros.expression.Expression
import com.lacker.micros.expression.Type
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class FloorTest {

    private val exp: Expression = mockk()
    private val floor: Floor = Floor(listOf(exp))

    @Test
    fun getType() {
        assertEquals(Type.Number, floor.type)
    }

    @Test
    fun eval() {
        every { exp.eval(any()) } returns 1.23
        assertEquals(1.0, floor.eval(emptyMap()))

        every { exp.eval(any()) } returns -1.23
        assertEquals(-2.0, floor.eval(emptyMap()))
    }

    @Test
    fun  toSql() {
        every { exp.toSql() } returns "1.23"
        assertEquals("FLOOR(1.23)", floor.toSql())
    }
}