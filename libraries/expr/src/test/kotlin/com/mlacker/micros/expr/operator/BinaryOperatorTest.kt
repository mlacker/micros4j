package com.mlacker.micros.expr.operator

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.Value
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

abstract class BinaryOperatorTest {

    protected abstract fun constructor(left: Expression, right: Expression): BinaryOperator

    protected val operator: BinaryOperator = constructor(5, 2)

    protected fun constructor(left: Any, right: Any) = constructor(Value(left), Value(right))

    @Test
    fun testToString() {
        assertEquals("5 ${operator.symbol} 2", operator.toString())
    }
}