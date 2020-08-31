package com.mlacker.micros.expression.operator.arithmetic

import com.mlacker.micros.expression.core.Constant
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class MinusTest : ArithmeticTest() {

    override fun constructor(x: Constant, y: Constant) = Minus(x, y)

    @Test
    fun eval() {
        assertEquals(5 - 2, eval(5, 2))
    }

    @Test
    fun toSql() {
        assertEquals("5 - 2", arithmetic.toSql())
    }
}