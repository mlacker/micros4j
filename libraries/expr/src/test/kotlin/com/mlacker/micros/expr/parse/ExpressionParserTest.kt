package com.mlacker.micros.expr.parse

import io.mockk.every
import io.mockk.mockk
import io.mockk.verifySequence
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class ExpressionParserTest {

    private val splitter = mockk<CharacterSplitter>()
    private val builder = mockk<ExpressionTreeBuilder>()
    private val parser = ExpressionParser(splitter, builder)

    @BeforeEach
    fun setup() {
        every { splitter.split(any()) } returns emptyList()
        every { builder.convert(any()) } returns emptyList()
        every { builder.build(any()) } returns mockk()
    }

    @Test
    fun parse() {
        val text = "1 + 2"

        parser.parse(text)

        verifySequence {
            splitter.split(eq(text))
            builder.convert(any())
            builder.build(any())
        }
    }
}