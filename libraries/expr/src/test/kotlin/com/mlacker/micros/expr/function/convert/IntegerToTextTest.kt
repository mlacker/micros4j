package com.mlacker.micros.expr.function.convert

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.types.BasicType
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class IntegerToTextTest {

    private val param0: Expression = mockk()
    private lateinit var integerTest: IntegerToText

    @BeforeEach
    fun setup() {
        every { param0.type } returns BasicType.Integer
        integerTest = IntegerToText(listOf(param0))
    }

    @Test
    fun getType() {
        assertEquals(BasicType.Text, integerTest.type)
    }

    @Test
    fun eval() {
        every { param0.eval(any()) } returns 1L
        assertEquals("1", integerTest.eval(emptyMap()))

        every { param0.eval(any()) } returns -1L
        assertEquals("-1", integerTest.eval(emptyMap()))
    }

    @Test
    fun testToString() {
        every { param0.toString() } returns "1"
        assertEquals("IntegerToText(1)", integerTest.toString())
    }
}