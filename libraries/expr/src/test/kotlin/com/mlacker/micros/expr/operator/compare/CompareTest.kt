package com.mlacker.micros.expr.operator.compare

import com.mlacker.micros.expr.operator.BinaryOperatorTest
import com.mlacker.micros.expr.types.BasicType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalDateTime

abstract class CompareTest : BinaryOperatorTest() {

    protected abstract fun compareTo0(): Boolean
    protected abstract fun compareTo1(): Boolean
    protected abstract fun compareTo2(): Boolean

    @Test
    fun `compare numerical eval`() {
        val emptyMap = emptyMap<String, Any>()

        assertEquals(compareTo0(), constructor(1L, 0L).eval(emptyMap))
        assertEquals(compareTo1(), constructor(1L, 1L).eval(emptyMap))
        assertEquals(compareTo2(), constructor(1L, 2L).eval(emptyMap))

        assertEquals(compareTo2(), constructor(1L, 10L).eval(emptyMap))
    }

    @Test
    fun `compare date eval`() {
        assertEquals(compareTo0(), constructor(LocalDate.now(), LocalDate.EPOCH).eval(emptyMap()))
        assertEquals(compareTo1(), constructor(LocalDate.now(), LocalDate.now()).eval(emptyMap()))
        assertEquals(compareTo2(), constructor(LocalDate.EPOCH, LocalDate.now()).eval(emptyMap()))

        assertEquals(compareTo0(), constructor(LocalDateTime.now(), LocalDate.now()).eval(emptyMap()))
    }

    @Test
    fun getType() {
        assertEquals(BasicType.Boolean, operator.type)
    }
}