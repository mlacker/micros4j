package com.lacker.micros.expression.operator.arithmetic

import com.lacker.micros.expression.core.Constant
import com.lacker.micros.expression.operator.BinaryOperator
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class PlusTest : ArithmeticTest() {

    override fun constructor(x: Constant, y: Constant): BinaryOperator = Plus(x, y)

    @Test
    fun eval() {
        assertEquals(1 + 1, eval(1, 1))
        assertEquals(1 + 1L, eval(1, 1L))
        assertEquals(1 + 1.0, eval(1, 1.0))
        assertEquals(1 + 1.0f, eval(1, 1.0f))
        assertEquals(1L + 1.0, eval(1L, 1.0))
        assertEquals(1.0 + 1.0f, eval(1.0, 1.0f))
        @Suppress("INTEGER_OVERFLOW")
        assertEquals(1 + Int.MAX_VALUE, eval(1, Int.MAX_VALUE))
    }

    @Test
    fun toSql() {
        assertEquals("5 + 2", arithmetic.toSql())
    }
}