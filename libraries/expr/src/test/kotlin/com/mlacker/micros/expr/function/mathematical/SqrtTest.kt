package com.mlacker.micros.expr.function.mathematical

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.types.BasicType
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal

internal class SqrtTest {

    private val param0: Expression = mockk()
    private lateinit var sqrt: Sqrt

    @BeforeEach
    fun setup() {
        every { param0.type } returns BasicType.Decimal
        sqrt = Sqrt(listOf(param0))
    }

    @Test
    fun getType() {
        assertEquals(BasicType.Decimal, sqrt.type)
    }

    @Test
    fun eval() {
        every { param0.eval(any()) } returns BigDecimal(4L)
        assertEquals(2L, sqrt.eval(emptyMap()))
    }

    @Test
    fun testToString() {
        every { param0.toString() } returns "-32"
        assertEquals("Sqrt(-32)", sqrt.toString())
    }
}