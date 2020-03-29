package com.lacker.micros.expression.parse

import com.lacker.micros.expression.Expression

interface ExpressionTreeBuilder {

    fun convert(segments: List<Segment>): List<Segment>

    fun build(segments: List<Segment>): Expression
}
