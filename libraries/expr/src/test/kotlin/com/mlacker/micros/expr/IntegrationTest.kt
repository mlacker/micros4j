package com.mlacker.micros.expr

import com.mlacker.micros.expr.parse.DefaultExpressionTreeBuilder
import com.mlacker.micros.expr.parse.ExpressionParser
import com.mlacker.micros.expr.parse.OperandCreator
import com.mlacker.micros.expr.parse.RegexCharacterSplitter
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.LocalDate

internal class IntegrationTest {

    private val splitter = RegexCharacterSplitter()
    private val builder = DefaultExpressionTreeBuilder(OperandCreator())
    private val parser = ExpressionParser(splitter, builder)

    @Test
    fun parse() {
        assertEval(3L, "1 + 2")
    }

    @Test
    fun mod() {
        assertEval(9L, "Mod(Abs(-9), 2) + 8")
    }

    @Test
    fun `nested function`() {
        assertEval(15L, "Length(Trim(\" zhsang san s a \",\"s\"))")
    }

    @Test
    fun `function with multi and optional arguments`() {
        assertEval("Hello world!", "Concat(\"Hello\", \" \", Substr(\"my world\", 3, 5), \"!\")")
    }

    @Test
    fun `operation with function`() {
        assertEval(BigDecimal(0.5), "3.0 / Round(6.0, 3)")
        assertEval(BigDecimal("0.333"), "Round(1.0, 3) / 3.0")
    }

    @Test
    fun `compare date type`() {
        assertEval(true, "CurrDate() <= CurrDateTime()")
    }

    @Test
    fun `compare date and variable`() {
        assertEquals(true, parser.parse("Out1 < CurrDate()").eval(mapOf("Out1" to LocalDate.EPOCH)))
    }

    @Test
    fun `parenthesis test`() {
        val text = "(1 + 2) * 3 - 4"

        val expr = parser.parse(text)
        val toString = expr.toString()
        val generate = expr.generate(emptyMap())

        assertEquals(text, toString)
        assertEquals(text, generate)
    }

    private fun assertEval(expected: Any, text: String) {
        val expr = parser.parse(text)

        val value = expr.eval(emptyMap())

        assertEquals(expected, value)
    }
}