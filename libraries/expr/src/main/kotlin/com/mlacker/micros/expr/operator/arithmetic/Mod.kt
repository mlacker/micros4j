package com.mlacker.micros.expr.operator.arithmetic

import com.mlacker.micros.expr.Expression
import java.math.BigDecimal

class Mod(leftOperand: Expression, rightOperand: Expression) : Arithmetic(leftOperand, rightOperand) {

    override fun eval(left: Long, right: Long): Long = left % right

    override fun eval(left: BigDecimal, right: BigDecimal): BigDecimal = left % right

    override val symbol: String = "%"

    override val precedence: Int = 3
}
