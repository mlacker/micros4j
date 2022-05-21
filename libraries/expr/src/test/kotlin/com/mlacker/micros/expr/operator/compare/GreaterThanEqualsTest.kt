package com.mlacker.micros.expr.operator.compare

import com.mlacker.micros.expr.Expression

class GreaterThanEqualsTest : CompareTest() {

    override fun constructor(left: Expression, right: Expression) = GreaterThanEquals(left, right)

    override fun compareTo0(): Boolean = true

    override fun compareTo1(): Boolean = true

    override fun compareTo2(): Boolean = false
}