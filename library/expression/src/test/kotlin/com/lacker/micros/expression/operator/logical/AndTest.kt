package com.lacker.micros.expression.operator.logical

import com.lacker.micros.expression.core.Constant
import com.lacker.micros.expression.Type
import com.lacker.micros.expression.Value
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class AndTest {

    private val expression: And = And(Value(), Value())

    @Test
    fun getType() {
        assertEquals(Type.Boolean, expression.type)
    }

    @Test
    fun eval() {
        assertAnd(true, true, true)
        assertAnd(false, true, false)
        assertAnd(false, false, true)
        assertAnd(false, false, false)
    }

    @Test
    fun toSql() {
        assertEquals("1 AND 1", expression.toSql())
    }

    private fun assertAnd(expected: Boolean, left: Boolean, right: Boolean) {
        assertEquals(expected, And(
                Constant(left, Type.Boolean),
                Constant(right, Type.Boolean)
        ).eval(emptyMap()))
    }
}