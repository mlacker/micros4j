package com.mlacker.micros.expr.function.logical

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.types.BasicType
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class IfTest {

    private val param0: Expression = mockk()
    private val param1: Expression = mockk()
    private val param2: Expression = mockk()
    private lateinit var exprIf: If

    @BeforeEach
    fun setup() {
        every { param0.type } returns BasicType.Boolean
        every { param1.type } returns BasicType.Integer
        every { param2.type } returns BasicType.Integer

        exprIf = If(listOf(param0, param1, param2))
    }

    @Test
    fun getType() {
        Assertions.assertEquals(param1.type, exprIf.type)
    }

    @Test
    fun `unknown type at param1`() {
        every { param1.type } returns BasicType.Unknown

        If(listOf(param0, param1, param2))
    }


    @Test
    fun eval() {
        every { param0.eval(any()) } returns (1 > 2) andThen (1 == 2) andThen (1 < 2)
        every { param1.eval(any()) } returns "1"
        every { param2.eval(any()) } returns 2 andThen 2
        Assertions.assertEquals(2, exprIf.eval(emptyMap()))
        Assertions.assertEquals(2, exprIf.eval(emptyMap()))
        Assertions.assertEquals("1", exprIf.eval(emptyMap()))
    }

    @Test
    fun testToString() {
        every { param0.toString() } returns "1 > 2"
        every { param1.toString() } returns "1"
        every { param2.toString() } returns "2"
        Assertions.assertEquals("If(1 > 2, 1, 2)", exprIf.toString())
    }
}