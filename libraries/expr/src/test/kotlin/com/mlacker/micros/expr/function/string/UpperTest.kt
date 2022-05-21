package com.mlacker.micros.expr.function.string

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.types.BasicType
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class UpperTest {


    private val param0: Expression = mockk()
    private lateinit var upper: Upper

    @BeforeEach
    fun setup() {
        every { param0.type } returns BasicType.Text
        upper = Upper(listOf(param0))
    }

    @Test
    fun getType() {
        assertEquals(BasicType.Text, upper.type)
    }

    @Test
    fun eval() {
        every { param0.eval(any()) } returns "hello"
        assertEquals("HELLO", upper.eval(emptyMap()))
    }

    @Test
    fun testToString() {
        every { param0.toString() } returns "'Hello '"
        assertEquals("Upper('Hello ')", upper.toString())
    }
}