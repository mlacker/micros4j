package com.mlacker.micros.expr.function.string

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.types.BasicType
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SubstrTest {
    private val param0: Expression = mockk()
    private val param1: Expression = mockk()
    private val param2: Expression = mockk()
    private lateinit var substr: Substr

    @BeforeEach
    fun setup() {
        every { param0.type } returns BasicType.Text
        every { param1.type } returns BasicType.Integer
        every { param2.type } returns BasicType.Integer
        substr = Substr(listOf(param0, param1, param2))
    }

    @Test
    fun getType() {
        Assertions.assertEquals(BasicType.Text, substr.type)
    }

    @Test
    fun eval() {
        every { param0.eval(any()) } returns "Hello world" andThen "Hello world"
        every { param1.eval(any()) } returns 0L andThen 2L
        every { param2.eval(any()) } returns 2L andThen 4L
        Assertions.assertEquals("He", substr.eval(emptyMap()))
        Assertions.assertEquals("llo ", substr.eval(emptyMap()))
    }

    @Test
    fun testToString() {
        every { param0.toString() } returns "'Hello '"
        every { param1.toString() } returns "2"
        every { param2.toString() } returns "3"
        Assertions.assertEquals("Substr('Hello ', 2, 3)", substr.toString())
    }
}