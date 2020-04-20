package com.lacker.micros.expression.function.string

import com.lacker.micros.expression.Expression
import com.lacker.micros.expression.Type
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class ConcatTest {

    private val param0: Expression = mockk()
    private val param1: Expression = mockk()
    private val concat: Concat = Concat(listOf(param0, param1))

    @Test
    fun getType() {
        assertEquals(Type.String, concat.type)
    }

    @Test
    fun eval() {
        every { param0.eval(any()) } returns "Hello "
        every { param1.eval(any()) } returns "world."
        assertEquals("Hello world.", concat.eval(emptyMap()))
    }

    @Test
    fun toSql() {
        every { param0.toSql() } returns "'Hello '"
        every { param1.toSql() } returns "'world.'"
        assertEquals("CONCAT('Hello ', 'world.')", concat.toSql())
    }
}