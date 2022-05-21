package com.mlacker.micros.expr.operator.logical

import com.mlacker.micros.expr.Constant
import com.mlacker.micros.expr.Value
import com.mlacker.micros.expr.types.BasicType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class OrTest {

    private val expression: Or = Or(Value(true), Value(true))

    @Test
    fun getType() {
        assertEquals(BasicType.Boolean, expression.type)
    }

    @Test
    fun eval() {
        assertAnd(true, true, true)
        assertAnd(true, true, false)
        assertAnd(true, false, true)
        assertAnd(false, false, false)
    }

    @Test
    fun testToString() {
        assertEquals("true | true", expression.toString())
    }

    private fun assertAnd(expected: Boolean, left: Boolean, right: Boolean) {
        assertEquals(
            expected, Or(
                Constant(left, BasicType.Boolean),
                Constant(right, BasicType.Boolean)
            ).eval(emptyMap())
        )
    }
}