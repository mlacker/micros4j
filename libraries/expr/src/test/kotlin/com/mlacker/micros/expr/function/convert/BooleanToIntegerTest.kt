package com.mlacker.micros.expr.function.convert

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.types.BasicType
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class BooleanToIntegerTest {

    private val param0: Expression = mockk()
    private lateinit var booleanToInteger: BooleanToInteger

    @BeforeEach
    fun setup() {
        every { param0.type } returns BasicType.Boolean
        booleanToInteger = BooleanToInteger(listOf(param0))
    }

    @Test
    fun getType() {
        assertEquals(BasicType.Integer, booleanToInteger.type)
    }

    @Test
    fun eval() {
        every { param0.eval(any()) } returns true
        assertEquals(1L, booleanToInteger.eval(emptyMap()))

        every { param0.eval(any()) } returns false
        assertEquals(0L, booleanToInteger.eval(emptyMap()))
    }

    @Test
    fun testToString() {
        every { param0.toString() } returns "true"
        assertEquals("BooleanToInteger(true)", booleanToInteger.toString())
    }
}