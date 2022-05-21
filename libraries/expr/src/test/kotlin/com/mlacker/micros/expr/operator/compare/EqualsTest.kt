package com.mlacker.micros.expr.operator.compare

import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.operator.BinaryOperatorTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDate

class EqualsTest : BinaryOperatorTest() {

    override fun constructor(left: Expression, right: Expression) = Equals(left, right)

    @Test
    fun `numeric test`() {
        assertEquals(true, eval(1, 1))
        assertEquals(false, eval(1, 0))
        assertEquals(false, eval(1, 1L))
    }

    @Test
    fun `text test`() {
        assertEquals(true, eval("1", "1"))
    }

    @Test
    fun `datetime test`() {
        assertEquals(true, eval(LocalDate.now(), LocalDate.now()))
        assertEquals(false, eval(LocalDate.now(), LocalDate.EPOCH))
    }

    private fun eval(left: Any, right: Any) = constructor(left, right).eval(emptyMap())
}