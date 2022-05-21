package com.mlacker.micros.expr.function.string

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.types.BasicType
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class ContainsTest {
    private val param0: Expression = mockk()
    private val param1: Expression = mockk()
    private lateinit var contains: Contains

    @BeforeEach
    fun setup() {
        every { param0.type } returns BasicType.Text
        every { param1.type } returns BasicType.Text
        contains = Contains(listOf(param0, param1))
    }

    @Test
    fun getSqlName() {
    }

    @Test
    fun getType() {
        assertEquals(BasicType.Boolean, contains.type)
    }

    @Test
    fun eval() {
        every { param0.eval(any()) } returns "Hello"
        every { param1.eval(any()) } returns "el"
        assertEquals(true, contains.eval(emptyMap()))
    }
}