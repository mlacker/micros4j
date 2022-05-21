package com.mlacker.micros.expr.parse

import com.mlacker.micros.expr.Expression

class ExpressionParser(
        private val splitter: CharacterSplitter,
        private val builder: ExpressionTreeBuilder
) {

    fun parse(text: String): Expression {
        // character split
        val segments = splitter.split(text)
        // build expression tree
        // validate
        val convert = builder.convert(segments)

        return builder.build(convert)
    }
}
