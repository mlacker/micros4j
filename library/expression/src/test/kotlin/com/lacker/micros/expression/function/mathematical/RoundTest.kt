package com.lacker.micros.expression.function.mathematical

import com.lacker.micros.expression.Expression
import com.lacker.micros.expression.Type
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class RoundTest {

    private val param0: Expression = mockk()
    private val param1: Expression = mockk()
    private val round: Round = Round(listOf(param0, param1))

    @Test
    fun getType() {
        assertEquals(Type.Number, round.type)
    }

    @Test
    fun eval() {
        every { param0.eval(any()) } returns -1.23 andThen -1.58 andThen 1.58 andThen 1.298 andThen 1.298 andThen 23.298
        every { param1.eval(any()) } returns 0 andThen 0 andThen 0 andThen 1 andThen 0 andThen -1

        assertEquals(-1.0, round.eval(emptyMap()))
        assertEquals(-2.0, round.eval(emptyMap()))
        assertEquals(2.0, round.eval(emptyMap()))
        assertEquals(1.3, round.eval(emptyMap()))
        assertEquals(1.0, round.eval(emptyMap()))
        assertEquals(20.0, round.eval(emptyMap()))
    }

    @Test
    fun toSql() {
        every { param0.toSql() } returns "1.298"
        every { param1.toSql() } returns "1"
        assertEquals("ROUND(1.298, 1)", round.toSql())
    }
}