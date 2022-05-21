package com.mlacker.micros.expr.function.convert

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.types.BasicType
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class BooleanToTextTest {

    private val param0: Expression = mockk()
    private lateinit var booleanToText: BooleanToText

    @BeforeEach
    fun setup() {
        every { param0.type } returns BasicType.Boolean
        booleanToText = BooleanToText(listOf(param0))
    }

    @Test
    fun getType() {
        assertEquals(BasicType.Text, booleanToText.type)
    }

    @Test
    fun eval() {
        every { param0.eval(any()) } returns true
        assertEquals("'true'", booleanToText.eval(emptyMap()))

        every { param0.eval(any()) } returns false
        assertEquals("'false'", booleanToText.eval(emptyMap()))
    }

    @Test
    fun testToString() {
        every { param0.toString() } returns "true"
        assertEquals("BooleanToText(true)", booleanToText.toString())
    }
}