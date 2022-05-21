package com.mlacker.micros.expr.function.mathematical

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.types.BasicType
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class RandIntegerTest {

    private val param0: Expression = mockk()
    private val param1: Expression = mockk()
    private lateinit var randInteger: RandInteger

    @BeforeEach
    fun setup() {
        every { param0.type } returns BasicType.Integer
        every { param1.type } returns BasicType.Integer
        randInteger = RandInteger(listOf(param0, param1))
    }

    @Test
    fun getType() {
        assertEquals(BasicType.Decimal, randInteger.type)
    }

    @Test
    fun eval() {
        every { param0.eval(any()) } returns 0L
        every { param1.eval(any()) } returns 5L

        repeat(5) {
            assert(randInteger.eval(emptyMap()) in 0L..5L)
        }
    }

    @Test
    fun testToString() {
        every { param0.toString() } returns "2"
        every { param1.toString() } returns "9"
        assertEquals("RandInteger(2, 9)", randInteger.toString())
    }
}