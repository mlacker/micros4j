package com.mlacker.micros.expr.function.convert

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.types.BasicType
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class IntegerToBooleanTest {

    private val param0: Expression = mockk()
    private lateinit var integerToBoolean: IntegerToBoolean

    @BeforeEach
    fun setup() {
        every { param0.type } returns BasicType.Integer
        integerToBoolean = IntegerToBoolean(listOf(param0))
    }

    @Test
    fun getType() {
        assertEquals(BasicType.Boolean, integerToBoolean.type)
    }

    @Test
    fun eval() {
        every { param0.eval(any()) } returns 121L
        assertEquals(true, integerToBoolean.eval(emptyMap()))

        every { param0.eval(any()) } returns -11L
        assertEquals(true, integerToBoolean.eval(emptyMap()))

        every { param0.eval(any()) } returns 0L
        assertEquals(false, integerToBoolean.eval(emptyMap()))
    }

    @Test
    fun testToString() {
        every { param0.toString() } returns "1"
        assertEquals("IntegerToBoolean(1)", integerToBoolean.toString())
    }
}