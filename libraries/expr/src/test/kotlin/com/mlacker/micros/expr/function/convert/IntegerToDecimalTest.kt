package com.mlacker.micros.expr.function.convert

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.types.BasicType
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal

internal class IntegerToDecimalTest {

    private val param0: Expression = mockk()
    private lateinit var integerToDecimal: IntegerToDecimal

    @BeforeEach
    fun setup() {
        every { param0.type } returns BasicType.Integer
        integerToDecimal = IntegerToDecimal(listOf(param0))
    }

    @Test
    fun getType() {
        assertEquals(BasicType.Decimal, integerToDecimal.type)
    }

    @Test
    fun eval() {
        every { param0.eval(any()) } returns 121L
        assertEquals(BigDecimal("121"), integerToDecimal.eval(emptyMap()))

        every { param0.eval(any()) } returns -11L
        assertEquals(BigDecimal("-11"), integerToDecimal.eval(emptyMap()))

        every { param0.eval(any()) } returns 0L
        assertEquals(BigDecimal.ZERO, integerToDecimal.eval(emptyMap()))
    }

    @Test
    fun testToString() {
        every { param0.toString() } returns "0"
        assertEquals("IntegerToDecimal(0)", integerToDecimal.toString())
    }
}