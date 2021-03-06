package com.mlacker.micros.expression.operator.arithmetic

import com.mlacker.micros.expression.Expression

class Times(leftOperand: Expression, rightOperand: Expression) : Arithmetic(leftOperand, rightOperand) {

    override fun eval(x: Int, y: Int): Int = x * y

    override fun eval(x: Long, y: Long): Long = x * y

    override fun eval(x: Float, y: Float): Float = x * y

    override fun eval(x: Double, y: Double): Double = x * y

    override fun symbol(): String = "*"
}