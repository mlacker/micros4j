package com.mlacker.micros.expression.parse

import com.mlacker.micros.expression.Expression
import com.mlacker.micros.expression.Type
import com.mlacker.micros.expression.core.Constant
import com.mlacker.micros.expression.function.string.Length
import com.mlacker.micros.expression.operator.arithmetic.Plus
import com.mlacker.micros.expression.operator.logical.Not
import java.util.*
import kotlin.collections.ArrayList

class DefaultExpressionTreeBuilder : ExpressionTreeBuilder {

    override fun convert(segments: List<Segment>): List<Segment> {
        val expressions = ArrayList<Segment>()
        val operators = Stack<Segment>()

        // 识别字符段类型
        // 识别运算符优先级
        // 转后缀表达式
        // 校验
        // 实例化表达式

        for (segment in segments) {
            if (segment.isOperator() || segment.isFunction()) {
                while (operators.size > 0) {
                    val peek = operators.peek()
                    if (segment.precedenceOf() < peek.precedenceOf()) {
                        break
                    }
                    if (peek.isOpeningParenthesis()) {
                        if (segment.isClosingParenthesis()) {
                            operators.pop()
                        }
                        break
                    }

                    expressions.add(operators.pop())
                }

                if (!segment.isClosingParenthesis()) {
                    operators.push(segment)
                }
            } else {
                expressions.add(segment)
            }
        }

        while (operators.size > 0) {
            expressions.add(operators.pop())
        }

        return expressions
    }

    override fun build(segments: List<Segment>): Expression {
        // 出栈异常处理
        val stacks = Stack<Expression>()
        for (segment in segments) {
            val expression = if (segment.isOperator() || segment.isFunction()) {
                // -运算符类型
                // 操作数顺序
                // 操作数数量
                when (segment.text) {
                    "+" -> Plus(stacks.pop(), stacks.pop())
                    "!" -> Not(stacks.pop())
                    "Length(" -> Length(listOf(stacks.pop()))
                    else -> TODO()
                }
            } else {
                // 操作数类型
                // 数据类型
                Constant(segment.text, Type.Number)
            }

            stacks.push(expression)
        }

        return stacks.pop()
    }
}