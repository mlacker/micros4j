package com.mlacker.micros.expression.operator.logical

import com.mlacker.micros.expression.Type
import com.mlacker.micros.expression.Value
import com.mlacker.micros.expression.core.Constant
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class NotTest {

    private val expression: Not = Not(Value())

    @Test
    fun getType() {
        assertEquals(Type.Boolean, expression.type)
    }

    @Test
    fun eval() {
        assertFalse { Not(Constant(true, Type.Boolean)).eval(emptyMap()) as Boolean }
        assertTrue { Not(Constant(false, Type.Boolean)).eval(emptyMap()) as Boolean }
    }

    @Test
    fun toSql() {
        assertEquals("NOT 1", expression.toSql())
    }
}