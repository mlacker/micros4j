package com.mlacker.micros.expression.parse

import com.mlacker.micros.expression.Expression
import com.mlacker.micros.expression.Value
import com.mlacker.micros.expression.core.Constant
import com.mlacker.micros.expression.function.string.Length
import com.mlacker.micros.expression.operator.arithmetic.Plus
import com.mlacker.micros.expression.operator.logical.Not
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class ExpressionTreeBuilderTest {

    private val builder: ExpressionTreeBuilder = DefaultExpressionTreeBuilder()

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
        val expression = buildExpression("'foo' Length(")

        if (expression is Length) {
            assertTrue(expression.parameters[0] is Constant)
        } else {
            fail()
        }
    }

    @Test
    fun plus() {
        val params = listOf(Value(1), Value(2))

//        val cons = Plus::class.primaryConstructor!!
//        val plus = cons.call(*params.toTypedArray()) as Expression

//        var cons: (params: List<Expression>) -> Expression = { Plus(params[0], params[1]) }
//        val plus = cons(params)

        val cons: (Expression, Expression) -> Plus = ::Plus
        val plus = cons.invoke(params[0], params[1])

        val eval = plus.eval(emptyMap())
        assertEquals(3, eval)
        println(plus)
    }

    private fun buildExpression(text: String): Expression =
            builder.build(text.split(' ').map { Segment.create(it) })
}