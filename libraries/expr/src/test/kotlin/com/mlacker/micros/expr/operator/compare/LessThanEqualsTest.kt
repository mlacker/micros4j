package com.mlacker.micros.expr.operator.compare

import com.mlacker.micros.expr.Expression

class LessThanEqualsTest : CompareTest() {

    override fun constructor(left: Expression, right: Expression) = LessThanEquals(left, right)

    override fun compareTo0(): Boolean = false

    override fun compareTo1(): Boolean = true

    override fun compareTo2(): Boolean = true
}