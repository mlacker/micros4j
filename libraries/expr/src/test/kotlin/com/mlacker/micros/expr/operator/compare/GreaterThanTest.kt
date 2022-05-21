package com.mlacker.micros.expr.operator.compare

import com.mlacker.micros.expr.Expression

class GreaterThanTest : CompareTest() {

    override fun constructor(left: Expression, right: Expression) = GreaterThan(left, right)

    override fun compareTo0(): Boolean = true

    override fun compareTo1(): Boolean = false

    override fun compareTo2(): Boolean = false
}