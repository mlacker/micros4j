package com.mlacker.micros.expr.function.string

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.types.BasicType
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class LengthTest {

    private val param0: Expression = mockk()
    private lateinit var length: Length

    @BeforeEach
    fun setup() {
        every { param0.type } returns BasicType.Text
        length = Length(listOf(param0))
    }

    @Test
    fun getType() {
        assertEquals(BasicType.Integer, length.type)
    }

    @Test
    fun eval() {
        every { param0.eval(any()) } returns "Hello world." andThen "Hello 世界。"

        assertEquals(12L, length.eval(emptyMap()))
        assertEquals(9L, length.eval(emptyMap()))
    }

    @Test
    fun testToString() {
        every { param0.toString() } returns "'Hello world.'"
        assertEquals("Length('Hello world.')", length.toString())
    }

}