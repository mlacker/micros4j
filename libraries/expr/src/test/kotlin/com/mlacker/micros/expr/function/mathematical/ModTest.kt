package com.mlacker.micros.expr.function.mathematical

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.types.BasicType
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class ModTest {
    private val param0: Expression = mockk()
    private val param1: Expression = mockk()
    private lateinit var mod: Mod

    @BeforeEach
    fun setup() {
        every { param0.type } returns BasicType.Decimal
        every { param1.type } returns BasicType.Decimal
        mod = Mod(listOf(param0, param1))
    }

    @Test
    fun getType() {
        Assertions.assertEquals(BasicType.Decimal, mod.type)
    }

    @Test
    fun eval() {
        every { param0.eval(any()) } returns BigDecimal(2)
        every { param1.eval(any()) } returns BigDecimal("1.5")
        Assertions.assertEquals(BigDecimal("0.5"), mod.eval(emptyMap()))

        every { param0.eval(any()) } returns -99L
        every { param1.eval(any()) } returns 3L
        Assertions.assertEquals(0L, mod.eval(emptyMap()))

        every { param0.eval(any()) } returns BigDecimal("22")
        every { param1.eval(any()) } returns 22L
        Assertions.assertEquals(0L, mod.eval(emptyMap()))
    }

    @Test
    fun testToString() {
        every { param0.toString() } returns "-32"
        every { param1.toString() } returns "66"
        Assertions.assertEquals("Mod(-32, 66)", mod.toString())
    }
}
