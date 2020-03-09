package com.lacker.micros.expression.operator.logical

import com.lacker.micros.expression.core.Constant
import com.lacker.micros.expression.Type
import com.lacker.micros.expression.Value
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