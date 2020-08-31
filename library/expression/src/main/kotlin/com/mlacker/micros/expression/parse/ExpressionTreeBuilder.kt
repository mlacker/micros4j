package com.mlacker.micros.expression.parse

import com.mlacker.micros.expression.Expression

interface ExpressionTreeBuilder {

    fun convert(segments: List<Segment>): List<Segment>

    fun build(segments: List<Segment>): Expression
}
