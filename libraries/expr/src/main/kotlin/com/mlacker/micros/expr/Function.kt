package com.mlacker.micros.expr

import com.mlacker.micros.expr.exception.ExpressionTypeException
import com.mlacker.micros.expr.exception.ParameterCountException
import com.mlacker.micros.expr.types.BasicType
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime

abstract class Function(val parameters: List<Expression>) : Expression {
    protected val name: String = this::class.simpleName!!

    override fun toString() = parameters.joinToString(
        separator = ", ",
        prefix = "$name(",
        postfix = ")"
    ) { it.toString() }

    protected fun assertType(parameters: List<Expression>, index: Int, vararg expectedTypes: Type) {
        assertType("$name 中第 ${index + 1} 个参数", parameters[index].type, *expectedTypes)
    }

    protected fun assertCount(parameters: List<Expression>, count: Int) {
        if (parameters.count() != count) {
            throw ParameterCountException("参数个数不匹配，正确个数为：${count}个")
        }
    }

    /**
     * 判断类型是否兼容 可以转换
     *
     * @param expectedType 预期类型
     * @param actualType   实际类型
     * @return 实际类型是否可以转换到目标类型
     */
    internal fun determineCompatibility(expectedType: Type, actualType: Type): Boolean {
        // 类型等于自己是直接兼容的
        if (expectedType == actualType) {
            return true
        }

        // 目标类型及其支持的隐式转换类型
        return CompatibleTypeMappings[expectedType]?.contains(actualType) ?: false
    }

    /**
     * 参数值类型转换函数
     * @param expectedType 目标类型
     * @param index 参数索引值
     * @param variables 参数列表
     * @return 转换后的函数 如果是不兼容的类型则抛出 TypeCastException 异常
     */
    internal fun <T : Any> convertToValue(expectedType: BasicType, index: Int, variables: Map<String, Any>): T? {
        if (!determineCompatibility(
                expectedType,
                parameters[index].type
            ) && parameters[index].type != BasicType.Unknown && parameters[index].type != BasicType.Any
        ) {
            throw ExpressionTypeException("$name 中第 ${index + 1} 个参数 表达式的类型不匹配，期待类型：${expectedType.name}，实际类型：${parameters[index].type}")
        }
        val value = parameters[index].eval(variables) ?: return null

        return when (expectedType) {
            BasicType.valueOf(value.javaClass.kotlin) -> value as T
            BasicType.Decimal -> BigDecimal.valueOf(value as Long) as T
            BasicType.DateTime -> (value as LocalDate).atStartOfDay() as T
            BasicType.Text -> (if (parameters[index].type == BasicType.DateTime) (value as LocalDateTime).toChinaString() else value.toString()) as T
            else -> throw TypeCastException()
        }
    }

    private fun assertType(target: String, actual: Type, vararg expectedTypes: Type) {
        if (actual == BasicType.Unknown || expectedTypes.contains(BasicType.Unknown)
            || actual == BasicType.Any || expectedTypes.contains(BasicType.Any)
        ) {
            return
        }

        if (expectedTypes.all { !determineCompatibility(it, actual) }) {
            throw ExpressionTypeException("$target 表达式的类型不匹配，期待类型：${expectedTypes.joinToString()}，实际类型：$actual")
        }
    }

    /**
     * 类型兼容性配置表
     */
    companion object {
        private val CompatibleTypeMappings = mapOf(
            BasicType.Decimal to listOf(BasicType.Integer),
            BasicType.DateTime to listOf(BasicType.Date),
            BasicType.Text to listOf(
                BasicType.Boolean,
                BasicType.Integer,
                BasicType.Decimal,
                BasicType.Date,
                BasicType.DateTime,
            )
        )
    }
}
