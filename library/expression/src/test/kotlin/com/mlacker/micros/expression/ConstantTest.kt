package com.mlacker.micros.expression

import com.mlacker.micros.expression.core.Constant
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


class ConstantTest {

    @Test
    fun eval() {
        constants()
                .mapKeys { Constant(it.value, it.key) }
                .forEach { assertEquals(it.value, it.key.eval(emptyMap())) }
    }

    @Test
    fun toSql() {
        assertEquals("1", Constant(true, Type.Boolean).toSql())
        assertEquals("12.05", Constant(12.05, Type.Number).toSql())
        assertEquals("'foo'", Constant("foo", Type.String).toSql())
    }

    @Test
    fun type() {
        constants()
                .mapValues { Constant(it.value, it.key) }
                .forEach { assertEquals(it.key, it.value.type) }
    }

    private fun constants() = mapOf(
            Type.Boolean to true,
            Type.Number to 12.05,
            Type.String to "foo"
    )
}