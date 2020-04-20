package com.lacker.micros.expression.parse

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
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
    }

    @Test
    fun `character split`() {
        val text = "1 + 2"

        parser.parse(text)

        verify { splitter.split(eq(text)) }
    }

    @Test
    fun `build expression tree`() {
        val text = "1 + 2"

        parser.parse(text)

        verify { builder.convert(any()) }
    }
}