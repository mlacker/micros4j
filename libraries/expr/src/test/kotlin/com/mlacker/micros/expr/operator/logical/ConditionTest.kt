package com.mlacker.micros.expr.operator.logical

import com.mlacker.micros.expr.Constant
import com.mlacker.micros.expr.Value
import com.mlacker.micros.expr.types.BasicType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ConditionTest {

    @Test
    fun getType() {
        assertEquals(BasicType.Integer, Condition(Value(), Value(), Value()).type)
        assertEquals(
            BasicType.Integer, Condition(
                Value(),
                Constant(1, BasicType.Integer),
                Constant(2, BasicType.Integer)
            ).type
        )
    }

    @Test
    fun eval() {
        assertEquals(
            1, Condition(
                Value(true), Value(1), Value(2)
            ).eval(emptyMap())
        )
        assertEquals(
            2, Condition(
                Value(false), Value(1), Value(2)
            ).eval(emptyMap())
        )
    }
}