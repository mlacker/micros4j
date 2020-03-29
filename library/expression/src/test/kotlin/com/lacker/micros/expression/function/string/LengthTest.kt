package com.lacker.micros.expression.function.string

import com.lacker.micros.expression.Expression
import com.lacker.micros.expression.Type
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class LengthTest {

    private val param0: Expression = mockk()
    private val length: Length = Length(listOf(param0))

    @Test
    fun getType() {
        assertEquals(Type.Number, length.type)
    }

    @Test
    fun eval() {
        every { param0.eval(any()) } returns "Hello world." andThen "Hello 世界。"

        assertEquals(12, length.eval(emptyMap()))
        assertEquals(9, length.eval(emptyMap()))
    }

    @Test
    fun toSql() {
        every { param0.toSql() } returns "'Hello world.'"
        assertEquals("CHAR_LENGTH('Hello world.')", length.toSql())
    }
}