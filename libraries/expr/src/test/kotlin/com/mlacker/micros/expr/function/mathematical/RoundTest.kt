package com.mlacker.micros.expr.function.mathematical

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.types.BasicType
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal

internal class RoundTest {

    private val param0: Expression = mockk()
    private val param1: Expression = mockk()
    private lateinit var round: Round

    @BeforeEach
    fun setup() {
        every { param0.type } returns BasicType.Decimal
        every { param1.type } returns BasicType.Integer
        round = Round(listOf(param0, param1))
    }

    @Test
    fun getType() {
        assertEquals(BasicType.Decimal, round.type)
    }

    @Test
    fun eval() {
        every { param0.eval(any()) } returns
                BigDecimal("123.452") andThen BigDecimal("123.452")
        every { param1.eval(any()) } returns 2L andThen -1L

        assertEquals(BigDecimal("123.45"), round.eval(emptyMap()))
        assertEquals(120L, round.eval(emptyMap()))
    }

    @Test
    fun testToString() {
        every { param0.toString() } returns "1.298"
        every { param1.toString() } returns "1"
        assertEquals("Round(1.298, 1)", round.toString())
    }
}