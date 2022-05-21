package com.mlacker.micros.expr.operator.logical

import com.mlacker.micros.expr.Constant
import com.mlacker.micros.expr.Value
import com.mlacker.micros.expr.types.BasicType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class AndTest {

    private val expression: And = And(Value(true), Value(true))

    @Test
    fun getType() {
        assertEquals(BasicType.Boolean, expression.type)
    }

    @Test
    fun eval() {
        assertAnd(true, true, true)
        assertAnd(false, true, false)
        assertAnd(false, false, true)
        assertAnd(false, false, false)
    }

    @Test
    fun testToString() {
        assertEquals("true & true", expression.toString())
    }

    private fun assertAnd(expected: Boolean, left: Boolean, right: Boolean) {
        assertEquals(
            expected, And(
                Constant(left, BasicType.Boolean),
                Constant(right, BasicType.Boolean)
            ).eval(emptyMap())
        )
    }
}