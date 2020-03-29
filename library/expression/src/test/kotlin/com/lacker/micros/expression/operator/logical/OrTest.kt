package com.lacker.micros.expression.operator.logical

import com.lacker.micros.expression.core.Constant
import com.lacker.micros.expression.Type
import com.lacker.micros.expression.Value
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class OrTest {

    private val expression: Or = Or(Value(), Value())

    @Test
    fun getType() {
        assertEquals(Type.Boolean, expression.type)
    }

    @Test
    fun eval() {
        assertAnd(true, true, true)
        assertAnd(true, true, false)
        assertAnd(true, false, true)
        assertAnd(false, false, false)
    }

    @Test
    fun toSql() {
        assertEquals("1 OR 1", expression.toSql())
    }

    private fun assertAnd(expected: Boolean, left: Boolean, right: Boolean) {
        assertEquals(expected, Or(
                Constant(left, Type.Boolean),
                Constant(right, Type.Boolean)
        ).eval(emptyMap()))
    }
}