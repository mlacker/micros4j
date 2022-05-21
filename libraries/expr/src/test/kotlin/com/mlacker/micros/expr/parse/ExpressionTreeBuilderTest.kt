package com.mlacker.micros.expr.parse

import com.mlacker.micros.expr.*
import com.mlacker.micros.expr.Function
import com.mlacker.micros.expr.exception.ExpressionResolveException
import com.mlacker.micros.expr.function.string.Length
import com.mlacker.micros.expr.operator.Dot
import com.mlacker.micros.expr.operator.arithmetic.Plus
import com.mlacker.micros.expr.operator.logical.Not
import com.mlacker.micros.expr.types.BasicType
import io.mockk.CapturingSlot
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

internal class ExpressionTreeBuilderTest {

    private val operandCreator: OperandCreator = mockk()
    private val builder: ExpressionTreeBuilder = DefaultExpressionTreeBuilder(operandCreator)

    @BeforeEach
    fun setup() {
        val slot = CapturingSlot<String>()
        every { operandCreator.create(capture(slot)) } answers {
            Constant(
                slot.captured,
                BasicType.Integer
            )
        }
        every { operandCreator.create("Foo") } returns Variable("Foo", BasicType.Unknown)
        every { operandCreator.create("Bar") } returns Variable("Bar", BasicType.Unknown)
    }

    @Test
    fun empty() {
        val expression = builder.build(emptyList())
        assertTrue(expression is Empty)
    }

    @Test
    fun operand() {
        val expression = buildExpression("1")

        assertTrue(expression is Constant)
    }

    @Test
    fun `binary operator`() {
        val expression = buildExpression("1 2 +")

        if (expression is Plus) {
            assertTrue(expression.leftOperand is Constant)
            assertTrue(expression.rightOperand is Constant)
        } else {
            fail()
        }
    }

    @Test
    fun `binary operation's operand order`() {
        val expression = buildExpression("1 2 +")

        if (expression is Plus) {
            assertEquals("1", (expression.leftOperand as Constant).value)
            assertEquals("2", (expression.rightOperand as Constant).value)
        } else {
            fail()
        }
    }

    @Test
    fun `single operator`() {
        val expression = buildExpression("true !")

        if (expression is Not) {
            assertTrue(expression.operand is Constant)
        } else {
            fail()
        }
    }

    @Test
    fun function() {
        every { operandCreator.create("\"foo\"") } answers { Constant("foo", BasicType.Text) }

        val expression = buildExpression("\"foo\" Length(")

        if (expression is Length) {
            assertTrue(expression.parameters[0] is Constant)
        } else {
            fail()
        }
    }

    @Test
    fun `function with optional arguments`() {
        every { operandCreator.create("\"foo\"") } answers { Constant("foo", BasicType.Text) }

        val text = "\"foo\" \"foo\" \"foo\" Concat("
        val segments = text.split(' ').map { Segment.create(it) }
        segments.last().parametersCount = 3

        val expr = builder.build(segments) as Function

        assertEquals(3, expr.parameters.size)
    }

    @Test
    fun variable() {
        val expression = buildExpression("Foo")

        assertTrue(expression is Variable)
    }

    @Test
    fun `property access`() {
        val expression = buildExpression("Foo Bar .")

        if (expression is Dot) {
            assertTrue(expression.operand is Variable)
            assertEquals("Foo", (expression.operand as Variable).key)
            assertEquals("Bar", expression.property.key)
        } else {
            fail()
        }
    }

    @Test
    fun `single operator then throws`() {
        assertThrows<EmptyStackException> { buildExpression("+") }
    }

    @Test
    fun `redundance element then throws`() {
        assertThrows<ExpressionResolveException> { buildExpression("1 2 + 3") }
    }

    private fun buildExpression(text: String): Expression =
        builder.build(text.split(' ').map { Segment.create(it) })
}
