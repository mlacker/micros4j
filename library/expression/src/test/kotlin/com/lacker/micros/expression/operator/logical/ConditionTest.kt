package com.lacker.micros.expression.operator.logical

import com.lacker.micros.expression.core.Constant
import com.lacker.micros.expression.Type
import com.lacker.micros.expression.Value
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