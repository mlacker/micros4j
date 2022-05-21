package com.mlacker.micros.expr.function.string

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.types.BasicType
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class LowerTest {

    private val param0: Expression = mockk()
    private lateinit var lower: Lower

    @BeforeEach
    fun setup() {
        every { param0.type } returns BasicType.Text
        lower = Lower(listOf(param0))
    }

    @Test
    fun getType() {
        assertEquals(BasicType.Text, lower.type)
    }

    @Test
    fun eval() {
        every { param0.eval(any()) } returns "HELLO"
        assertEquals("hello", lower.eval(emptyMap()))
    }

    @Test
    fun testToString() {
        every { param0.toString() } returns "'Hello '"
        assertEquals("Lower('Hello ')", lower.toString())
    }
}