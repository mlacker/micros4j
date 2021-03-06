package com.mlacker.micros.expression.operator.logical

import com.mlacker.micros.expression.Type
import com.mlacker.micros.expression.Value
import com.mlacker.micros.expression.core.Constant
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ConditionTest {

    @Test
    fun getType() {
        assertEquals(Type.String, Condition(Value(), Value(), Value()).type)
        assertEquals(Type.Number, Condition(
            Value(), Constant(1, Type.Number), Constant(2, Type.Number)).type)
    }

    @Test
    fun eval() {
        assertEquals(1, Condition(
            Value(true), Value(1), Value(2)).eval(emptyMap()))
        assertEquals(2, Condition(
            Value(false), Value(1), Value(2)).eval(emptyMap()))
    }
}