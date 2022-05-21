package com.mlacker.micros.expr.parse

import com.mlacker.micros.expr.Expression

interface ExpressionTreeBuilder {

    fun convert(segments: List<Segment>): List<Segment>

    fun build(segments: List<Segment>): Expression
}
