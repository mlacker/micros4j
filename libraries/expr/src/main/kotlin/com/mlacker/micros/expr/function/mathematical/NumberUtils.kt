package com.mlacker.micros.expr.function.mathematical

import com.mlacker.micros.expr.exception.ExpressionTypeException
import java.math.BigDecimal
import kotlin.math.floor

class NumberUtils {
    companion object {
        fun isLong(num: Any): Boolean = num is Long
        fun isBigDecimal(num: Any): Boolean = num is BigDecimal

        fun getLong(number: Any) = when (number) {
            is BigDecimal -> number.toLong()
            is Int -> number.toLong()
            else -> throw ExpressionTypeException("错误的number值类型" + number::class.java.name)
        }

        fun getBigDecimal(number: Any) = when (number) {
            is BigDecimal -> number
            is Int -> BigDecimal(number)
            else -> throw ExpressionTypeException("错误的number值类型" + number::class.java.name)
        }

        fun getInt(number: Any) = when (number) {
            is BigDecimal -> number.toInt()
            is Int -> number
            else -> throw ExpressionTypeException("错误的number值类型" + number::class.java.name)
        }

        fun getDouble(number: Any) = when (number) {
            is BigDecimal -> number.toDouble()
            is Long -> number.toDouble()
            else -> throw ExpressionTypeException("错误的number值类型" + number::class.java.name)
        }

        private fun isIntReal(number: Double) = number - floor(number) < 1e-10

        private fun isIntReal(number: BigDecimal): Boolean = !number.toPlainString().contains(".")

        fun convertReturnValue(number: BigDecimal) = if (isIntReal(number)) number.toLong() else number

        fun convertReturnValue(number: Double) = if (isIntReal(number)) number.toLong() else BigDecimal(number)
    }

}
