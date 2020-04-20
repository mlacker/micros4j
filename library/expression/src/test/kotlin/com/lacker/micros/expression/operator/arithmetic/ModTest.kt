package com.lacker.micros.expression.operator.arithmetic

import com.lacker.micros.expression.core.Constant
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ModTest : ArithmeticTest() {

    override fun constructor(x: Constant, y: Constant) = Mod(x, y)

    @Test
    fun eval() {
        assertEquals(5 % 2, eval(5, 2))
    }

    @Test
    fun toSql() {
        assertEquals("5 % 2", arithmetic.toSql())
    }

}