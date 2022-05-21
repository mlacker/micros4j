package com.mlacker.micros.expr.function.string

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.types.BasicType
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class TrimTest {

    private val param0: Expression = mockk()
    private val param1: Expression = mockk()
    private lateinit var trim: Trim

    @BeforeEach
    fun setup() {
        every { param0.type } returns BasicType.Text
        every { param1.type } returns BasicType.Text
        trim = Trim(listOf(param0, param1))
    }

    @Test
    fun getType() {
        assertEquals(BasicType.Text, trim.type)
    }

    @Test
    fun eval() {
        every { param0.eval(any()) } returns " zhang san " andThen " zhang san " andThen " zhang san " andThen " zhang san "
        every { param1.eval(any()) } returns "s" andThen "e" andThen "b" andThen "a"
        assertEquals("zhang san ", trim.eval(emptyMap()))
        assertEquals(" zhang san", trim.eval(emptyMap()))
        assertEquals("zhang san", trim.eval(emptyMap()))
    }

    @Test
    fun testToString() {
        every { param0.toString() } returns "' zhang san '"
        every { param1.toString() } returns "'b'"
        assertEquals("Trim(' zhang san ', 'b')", trim.toString())
    }
}