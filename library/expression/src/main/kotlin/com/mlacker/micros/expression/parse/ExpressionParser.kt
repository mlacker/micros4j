package com.mlacker.micros.expression.parse

import com.mlacker.micros.expression.Expression

class ExpressionParser(
        private val splitter: CharacterSplitter,
        private val builder: ExpressionTreeBuilder
) {

    fun parse(text: String): Expression {
        // character split
        val segments = splitter.split(text)
        // build expression tree
        //  validate
        builder.convert(segments)

        TODO()
    }
}