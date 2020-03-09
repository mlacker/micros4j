package com.lacker.micros.expression.operator.arithmetic

import com.lacker.micros.expression.Expression
import com.lacker.micros.expression.Type
import com.lacker.micros.expression.operator.BinaryOperator

abstract class Arithmetic(leftOperand: Expression, rightOperand: Expression)
    : BinaryOperator(leftOperand, rightOperand) {

    final override val type = Type.Number

    final override fun eval(variables: Map<String, Any>): Any {
        val x: Number = leftOperand.eval(variables) as Number
        val y: Number = rightOperand.eval(variables) as Number

        return when {
            x is Double || y is Double -> eval(x.toDouble(), y.toDouble())
            x is Float || y is Float -> eval(x.toFloat(), y.toFloat())
            x is Long || y is Long -> eval(x.toLong(), y.toLong())
            else -> eval(x.toInt(), y.toInt())
        }
    }

    protected abstract fun eval(x: Int, y: Int): Int

    protected abstract fun eval(x: Long, y: Long): Long

    protected abstract fun eval(x: Float, y: Float): Float

    protected abstract fun eval(x: Double, y: Double): Double
}