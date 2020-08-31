package com.mlacker.micros.expression.operator.arithmetic

import com.mlacker.micros.expression.Type
import com.mlacker.micros.expression.core.Constant
import com.mlacker.micros.expression.operator.BinaryOperator
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

abstract class ArithmeticTest {

    protected abstract fun constructor(x: Constant, y: Constant): BinaryOperator

    protected val arithmetic: BinaryOperator = constructor(5, 2)

    protected fun eval(x: Number, y: Number) = constructor(x, y).eval(emptyMap())

    @Test
    fun getType() {
        assertEquals(Type.Number, arithmetic.type)
    }

    private fun constructor(x: Number, y: Number) = constructor(Constant(x, Type.Number), Constant(y, Type.Number))
}
