package com.mlacker.micros.expr.operator.arithmetic

import com.mlacker.micros.expr.Expression
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PlusTest : ArithmeticTest() {

    override fun constructor(left: Expression, right: Expression) = Plus(left, right)

    @Test
    fun eval() {
        assertEquals(1L + 1L, eval(1L, 1L))
        assertEquals((1L + 1.0).toBigDecimal(), eval(1L, 1.0.toBigDecimal()))
        assertEquals((1.0 + 1.0).toBigDecimal(), eval(1.0.toBigDecimal(), 1.0.toBigDecimal()))
        @Suppress("INTEGER_OVERFLOW")
        assertEquals(1L + Long.MAX_VALUE, eval(1L, Long.MAX_VALUE))
    }
}