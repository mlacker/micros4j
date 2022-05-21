package com.mlacker.micros.expr.operator.compare

import com.mlacker.micros.expr.Expression

class LessThanTest : CompareTest() {

    override fun constructor(left: Expression, right: Expression) = LessThan(left, right)

    override fun compareTo0(): Boolean = false

    override fun compareTo1(): Boolean = false

    override fun compareTo2(): Boolean = true
}