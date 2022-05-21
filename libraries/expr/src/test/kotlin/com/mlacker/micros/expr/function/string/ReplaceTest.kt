package com.mlacker.micros.expr.function.string

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.types.BasicType
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class ReplaceTest {
    private val param0: Expression = mockk()
    private val param1: Expression = mockk()
    private val param2: Expression = mockk()
    private lateinit var replace: Replace

    @BeforeEach
    fun setup() {
        every { param0.type } returns BasicType.Text
        every { param1.type } returns BasicType.Text
        every { param2.type } returns BasicType.Text

        replace = Replace(listOf(param0, param1, param2))
    }

    @Test
    fun getType() {
        assertEquals(BasicType.Text, replace.type)
    }

    @Test
    fun eval() {
        every { param0.eval(any()) } returns "Hello"
        every { param1.eval(any()) } returns "e"
        every { param2.eval(any()) } returns "s"
        assertEquals("Hsllo", replace.eval(emptyMap()))
    }

    @Test
    fun testToString() {
        every { param0.toString() } returns "'Hello'"
        every { param1.toString() } returns "'e'"
        every { param2.toString() } returns "'s'"
        assertEquals("Replace('Hello', 'e', 's')", replace.toString())
    }
}