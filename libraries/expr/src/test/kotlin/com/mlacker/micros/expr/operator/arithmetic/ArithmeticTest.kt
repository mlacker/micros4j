package com.mlacker.micros.expr.operator.arithmetic

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.operator.BinaryOperatorTest
import com.mlacker.micros.expr.types.BasicType
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

abstract class ArithmeticTest : BinaryOperatorTest() {

    protected fun eval(left: Number, right: Number) = constructor(left, right).eval(emptyMap())

    @Test
    fun getType() {
        assertEquals(BasicType.Integer, operator.type)
    }

    @Test
    fun `type compatibility`() {
        val integer = mockk<Expression> { every { type } returns BasicType.Integer }
        val decimal = mockk<Expression> { every { type } returns BasicType.Decimal }

        assertEquals(BasicType.Integer, constructor(integer, integer).type)
        assertEquals(BasicType.Decimal, constructor(decimal, decimal).type)
        assertEquals(BasicType.Decimal, constructor(integer, decimal).type)
        assertEquals(BasicType.Decimal, constructor(decimal, integer).type)
    }
}
