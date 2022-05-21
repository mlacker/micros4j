package com.mlacker.micros.expr.operator.arithmetic

import com.mlacker.micros.expr.Expression
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TimesTest : ArithmeticTest() {

    override fun constructor(left: Expression, right: Expression) = Times(left, right)

    @Test
    fun eval() {
        assertEquals(5L * 2, eval(5L, 2L))
    }
}