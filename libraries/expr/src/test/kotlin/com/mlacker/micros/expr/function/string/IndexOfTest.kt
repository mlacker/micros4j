package com.mlacker.micros.expr.function.string

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.types.BasicType
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class IndexOfTest {
    private val param0: Expression = mockk()
    private val param1: Expression = mockk()
    private val param2: Expression = mockk()
    private lateinit var indexOf: IndexOf

    @BeforeEach
    fun setup() {
        every { param0.type } returns BasicType.Text
        every { param1.type } returns BasicType.Text
        every { param2.type } returns BasicType.Boolean
        indexOf = IndexOf(listOf(param0, param1, param2))
    }

    @Test
    fun getType() {
        Assertions.assertEquals(BasicType.Integer, indexOf.type)
    }

    @Test
    fun eval() {
        every { param0.eval(any()) } returns "Hello world" andThen "Hello world" andThen "Hello world"
        every { param1.eval(any()) } returns "He" andThen "Worl" andThen "he"
        every { param2.eval(any()) } returns false andThen true andThen false
        Assertions.assertEquals(0L, indexOf.eval(emptyMap()))
        Assertions.assertEquals(6L, indexOf.eval(emptyMap()))
        Assertions.assertEquals(-1L, indexOf.eval(emptyMap()))
    }

    @Test
    fun testToString() {
        every { param0.toString() } returns "'Hello world'"
        every { param1.toString() } returns "'He'"
        every { param2.toString() } returns "true"
        Assertions.assertEquals("IndexOf('Hello world', 'He', true)", indexOf.toString())
    }
}