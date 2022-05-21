package com.mlacker.micros.expr.function.string

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.types.BasicType
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class LeftTest {

    private val param0: Expression = mockk()
    private val param1: Expression = mockk()
    private lateinit var left: Left

    @BeforeEach
    fun setup() {
        every { param0.type } returns BasicType.Text
        every { param1.type } returns BasicType.Integer
        left = Left(listOf(param0, param1))
    }

    @Test
    fun getType() {
        assertEquals(BasicType.Text, left.type)
    }


    @Test
    fun eval() {
        every { param0.eval(any()) } returns "Hello"
        every { param1.eval(any()) } returns 2L
        assertEquals("He", left.eval(emptyMap()))
    }

    @Test
    fun testToString() {
        every { param0.toString() } returns "'Hello '"
        every { param1.toString() } returns "2"
        assertEquals("Left('Hello ', 2)", left.toString())
    }


}