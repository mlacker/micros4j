package com.mlacker.micros.expr.parse

import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertIterableEquals
import org.junit.jupiter.api.Test

internal class PostfixConverterTest {

    private val converter: ExpressionTreeBuilder = DefaultExpressionTreeBuilder(mockk())

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
    fun `function with optional parameters`() {
        val text = "Concat( 1 , 2 , 3 )"
        val expected = "1 2 3 Concat("

        val segments = converter.convert(text.split(' ').map { Segment.create(it) })

        assertIterableEquals(
            expected.split(' '),
            segments.map { it.text }
        )
        assertEquals(3, segments.last().parametersCount)
    }

    @Test
    fun `nested function with optional parameters`() {
        val text = "Concat( 4 , Concat( 1 , 2 , 3 ) , 5 , 6 )"
        val expected = "4 1 2 3 Concat( 5 6 Concat("

        val segments = converter.convert(text.split(' ').map { Segment.create(it) })

        assertIterableEquals(
            expected.split(' '),
            segments.map { it.text }
        )
        assertEquals(4, segments.last().parametersCount)
    }

    @Test
    fun `operation with function`() {
        assertConvert("1 + Add( 2 3 ) + 4", "1 2 3 Add( + 4 +")
    }

    @Test
    fun `nested function`() {
        assertConvert("Substring( Foo 0 Length( Foo ) )", "Foo 0 Foo Length( Substring(")
    }

    @Test
    fun `function with parameters and operation`() {
        assertConvert("If( 2 > 1 , 3 , 4 )", "2 1 > 3 4 If(")
    }

    @Test
    fun `unary operation`() {
        assertConvert("true & ! false", "true false ! &")
    }

    @Test
    fun `properties access`() {
        assertConvert("Foo . Bar", "Foo Bar .")
    }

    @Test
    fun `index access`() {
        assertConvert("Foo . Bar [0]", "Foo Bar . [0]")
    }

    private fun assertConvert(text: String, expected: String = "") {
        assertIterableEquals(
            expected.split(' '),
            converter.convert(text.split(' ').map { Segment.create(it) }).map { it.text }
        )
    }
}