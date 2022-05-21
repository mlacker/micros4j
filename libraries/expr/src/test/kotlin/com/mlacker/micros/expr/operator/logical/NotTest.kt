package com.mlacker.micros.expr.operator.logical

import com.mlacker.micros.expr.Constant
import com.mlacker.micros.expr.Value
import com.mlacker.micros.expr.types.BasicType
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class NotTest {

    private val expression: Not = Not(Value(true))

    @Test
    fun getType() {
        assertEquals(BasicType.Boolean, expression.type)
    }

    @Test
    fun eval() {
        assertFalse { Not(Constant(true, BasicType.Boolean)).eval(emptyMap()) as Boolean }
        assertTrue { Not(Constant(false, BasicType.Boolean)).eval(emptyMap()) as Boolean }
    }

    @Test
    fun testToString() {
        assertEquals("!true", expression.toString())
    }
}