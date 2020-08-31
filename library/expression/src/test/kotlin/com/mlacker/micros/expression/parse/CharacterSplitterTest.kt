package com.mlacker.micros.expression.parse

import org.junit.jupiter.api.Assertions.assertIterableEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class CharacterSplitterTest {

    private val splitter: CharacterSplitter = RegexCharacterSplitter()

    @Test
    fun empty() {
        assertSplit("")
    }

    @Test
    fun `one-digit`() {
        assertSplit("1", "1")
    }

    @Test
    fun `multi-digit`() {
        assertSplit("123", "123")
    }

    @Test
    fun decimal() {
        assertSplit("3.1415926", "3.1415926")
    }

    @Test
    fun negative() {
        assertSplit("-1", "-1")
    }

    @Test
    fun boolean() {
        assertSplit("true", "true")
        assertSplit("false", "false")
    }

    @Test
    fun `empty string`() {
        assertSplit("''", "''")
    }

    @Test
    fun string() {
        assertSplit("'foo'", "'foo'")
    }

    @Test
    fun `number string`() {
        assertSplit("'123'", "'123'")
    }

    @Test
    fun `data string`() {
        assertSplit("'2018-10-01 12:00:00'", "'2018-10-01 12:00:00'")
    }

    @Test
    fun `addition string`() {
        assertSplit("'foo' + 'bar'", "'foo'", "+", "'bar'")
    }

    @Test
    fun addition() {
        assertSplit("1 + 1", "1", "+", "1")
    }

    @Test
    fun `addition without space`() {
        assertSplit("1+1", "1", "+", "1")
    }

    @Test
    fun `addition negative without space`() {
        assertSplit("1+-1", "1", "+", "-1")
    }

    @Test
    fun `addition with multi-space`() {
        assertSplit("  1   +    1     ", "1", "+", "1")
    }

    @Test
    fun `arithmetic operation`() {
        assertSplit("1 + 2 - 3 * 4 / 5 % 6", "1", "+", "2", "-", "3", "*", "4", "/", "5", "%", "6")
    }

    @Test
    fun `compare operation`() {
        assertSplit("1 == 2 != 3 > 4 >= 5 < 6 <= 7",
                "1", "==", "2", "!=", "3", ">", "4", ">=", "5", "<", "6", "<=", "7")
    }

    @Test
    fun `logic operation`() {
        assertSplit("!false & true | false", "!", "false", "&", "true", "|", "false")
    }

    @Test
    fun `space splice`() {
        assertSplit("1 2  3", "1", "2", "3")
    }

    @Test
    fun `comma splice`() {
        assertSplit("1,2, 3", "1", "2", "3")
    }

    @Test
    fun brackets() {
        assertSplit("1 + (2 + 3)", "1", "+", "(", "2", "+", "3", ")")
    }

    @Test
    fun `brackets with negative`() {
        assertSplit("1 + (-2 + 3)", "1", "+", "(", "-2", "+", "3", ")")
    }

    @Test
    fun function() {
        assertSplit("Now()", "Now(", ")")
    }

    @Test
    fun `function with parameters`() {
        assertSplit("Add(1, 2)", "Add(", "1", "2", ")")
    }

    @Test
    fun `nested function`() {
        assertSplit("Add(1, Add(2, 3))", "Add(", "1", "Add(", "2", "3", ")", ")")
    }

    @Test
    fun `error number`() {
        assertThrows<ParseException> { splitter.split("1d3") }
    }

    @Test
    fun `error string`() {
        assertThrows<ParseException> { splitter.split("'foo'boo'") }
        assertThrows<ParseException> { splitter.split("'foo''boo") }
    }

    @Test
    fun `error comma`() {
        assertThrows<ParseException> { splitter.split("1, 2, , 3") }
    }

    private fun assertSplit(text: String, vararg segments: String) {
        assertIterableEquals(segments.asList(), splitter.split(text).map { it.text })
    }
}