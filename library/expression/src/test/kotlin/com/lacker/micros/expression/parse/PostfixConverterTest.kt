package com.lacker.micros.expression.parse

import org.junit.jupiter.api.Assertions.assertIterableEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class PostfixConverterTest {

    private val converter: ExpressionTreeBuilder = DefaultExpressionTreeBuilder()

    @Test
    fun const() {
        assertConvert("1", "1")
    }

    @Test
    fun `addition arithmetic operation`() {
        assertConvert("1 + 2", "1 2 +")
    }

    @Test
    fun `multiply arithmetic operation`() {
        assertConvert("1 * 2", "1 2 *")
    }

    @Test
    fun associativity() {
        assertConvert("1 + 2 - 3 + 4", "1 2 + 3 - 4 +")
    }

    @Test
    fun precedence() {
        assertConvert("1 + 2 * 3 - 4", "1 2 3 * + 4 -")
    }

    @Test
    fun parenthesis() {
        assertConvert("1 * ( 2 + 3 )", "1 2 3 + *")
    }

    @Test
    fun `nested parenthesis`() {
        assertConvert("( 1 + 2 ) * ( ( 3 + 4 ) - 5 )", "1 2 + 3 4 + 5 - *")
    }

    @Test
    fun `function without parameters`() {
        assertConvert("Now( )", "Now(")
    }

    @Test
    fun `function with parameters`() {
        assertConvert("Add( 1 2 )", "1 2 Add(")
    }

    @Test
    fun `unary operation`() {
        assertConvert("true & ! false", "true false ! &")
    }

    fun `single operator then throws`() {
        assertThrows<ParseException> { assertConvert("+") }
    }

    private fun assertConvert(text: String, expected: String = "") {
        assertIterableEquals(
                expected.split(' '),
                converter.convert(text.split(' ').map { Segment.create(it) }).map { it.text }
        )
    }
}