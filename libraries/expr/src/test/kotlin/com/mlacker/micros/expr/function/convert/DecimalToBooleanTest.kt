package com.mlacker.micros.expr.function.convert

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.types.BasicType
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal

internal class DecimalToBooleanTest {

    private val param0: Expression = mockk()
    private lateinit var decimalToBoolean: DecimalToBoolean

    @BeforeEach
    fun setup() {
        every { param0.type } returns BasicType.Decimal
        decimalToBoolean = DecimalToBoolean(listOf(param0))
    }

    @Test
    fun getType() {
        assertEquals(BasicType.Boolean, decimalToBoolean.type)
    }

    @Test
    fun eval() {
        every { param0.eval(any()) } returns BigDecimal.ZERO
        assertEquals(false, decimalToBoolean.eval(emptyMap()))

        every { param0.eval(any()) } returns BigDecimal("11.223")
        assertEquals(true, decimalToBoolean.eval(emptyMap()))
    }

    @Test
    fun testToString() {
        every { param0.toString() } returns "111.2"
        assertEquals("DecimalToBoolean(111.2)", decimalToBoolean.toString())
    }
}