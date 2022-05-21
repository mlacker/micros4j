package com.mlacker.micros.expr.function.string

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.types.BasicType
import com.mlacker.micros.expr.types.ListType
import com.mlacker.micros.expr.types.LogicList
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SplitTest {

    private val param0: Expression = mockk()
    private val param1: Expression = mockk()
    private lateinit var split: Split

    @BeforeEach
    fun setup() {
        every { param0.type } returns BasicType.Text
        every { param1.type } returns BasicType.Text
        split = Split(listOf(param0, param1))
    }

    @Test
    fun getType() {
        assertTrue(split.type is ListType)
        assertEquals(BasicType.Text, (split.type as ListType).innerType)
    }

    @Test
    fun eval() {
        every { param0.eval(any()) } returns "Foo, Bar, Baz"
        every { param1.eval(any()) } returns ", "

        val list = split.eval(emptyMap())

        if (list is LogicList<*>) {
            assertEquals(3, list.size)
            assertEquals("Foo", list[0])
            assertEquals("Bar", list[1])
            assertEquals("Baz", list[2])
        } else {
            fail()
        }
    }

    @Test
    fun testToString() {
        every { param0.toString() } returns "Foo"
        every { param1.toString() } returns "Bar"
        assertEquals("Split(Foo, Bar)", split.toString())
    }
}