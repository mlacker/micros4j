package com.mlacker.micros.expr.parse

import com.mlacker.micros.expr.Empty
import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.Variable
import com.mlacker.micros.expr.exception.ExpressionResolveException
import com.mlacker.micros.expr.function.convert.*
import com.mlacker.micros.expr.function.datetime.*
import com.mlacker.micros.expr.function.format.FormatDateTime
import com.mlacker.micros.expr.function.format.FormatDecimal
import com.mlacker.micros.expr.function.format.FormatPercent
import com.mlacker.micros.expr.function.logical.If
import com.mlacker.micros.expr.function.logical.IsNull
import com.mlacker.micros.expr.function.mathematical.*
import com.mlacker.micros.expr.function.session.GetUserId
import com.mlacker.micros.expr.function.string.*
import com.mlacker.micros.expr.operator.Dot
import com.mlacker.micros.expr.operator.arithmetic.Div
import com.mlacker.micros.expr.operator.arithmetic.Minus
import com.mlacker.micros.expr.operator.arithmetic.Mod
import com.mlacker.micros.expr.operator.arithmetic.Plus
import com.mlacker.micros.expr.operator.arithmetic.Times
import com.mlacker.micros.expr.operator.compare.*
import com.mlacker.micros.expr.operator.logical.And
import com.mlacker.micros.expr.operator.logical.Not
import com.mlacker.micros.expr.operator.logical.Or
import java.util.*

class DefaultExpressionTreeBuilder(private val operandCreator: OperandCreator) : ExpressionTreeBuilder {

    override fun convert(segments: List<Segment>): List<Segment> {
        val expressions = ArrayList<Segment>()
        val operators = Stack<Segment>()
        val parameterCountStack = Stack<Int>()

        for (segment in segments) {
            if (segment.isOperator() || segment.isFunction()) {
                while (operators.size > 0) {
                    val peek = operators.peek()
                    if (segment.precedenceOf() < peek.precedenceOf()) {
                        break
                    }
                    if (peek.isOpeningParenthesis()) {
                        if (segment.isClosingParenthesis()) {
                            if (peek.isFunction()) {
                                peek.parametersCount = parameterCountStack.pop() + 1
                                expressions.add(operators.pop())
                            } else {
                                operators.pop()
                            }
                        }
                        break
                    }

                    expressions.add(operators.pop())
                }

                if (segment.isFunction()) {
                    parameterCountStack.push(0)
                } else if (segment.isComma()) {
                    parameterCountStack.push(parameterCountStack.pop() + 1)
                }

                if (!segment.isClosingParenthesis() && !segment.isComma()) {
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
        if (segments.isEmpty()) {
            return Empty()
        }

        // 出栈异常处理
        val stacks = Stack<Expression>()
        for (segment in segments) {
            val expression = if (segment.isOperator() || segment.isFunction()) {
                // -运算符类型
                // 操作数顺序
                // 操作数数量
                when (segment.text) {
                    "." -> {
                        val property = stacks.pop() as Variable
                        Dot(stacks.pop(), property)
                    }
                    "!" -> Not(stacks.pop())
                    "*" -> buildBinaryOperator(::Times, stacks)
                    "/" -> buildBinaryOperator(::Div, stacks)
                    "%" -> buildBinaryOperator(::Mod, stacks)
                    "+" -> buildBinaryOperator(::Plus, stacks)
                    "-" -> buildBinaryOperator(::Minus, stacks)
                    "<" -> buildBinaryOperator(::LessThan, stacks)
                    "<=" -> buildBinaryOperator(::LessThanEquals, stacks)
                    ">" -> buildBinaryOperator(::GreaterThan, stacks)
                    ">=" -> buildBinaryOperator(::GreaterThanEquals, stacks)
                    "==" -> buildBinaryOperator(::Equals, stacks)
                    "!=" -> buildBinaryOperator(::NotEquals, stacks)
                    "&" -> buildBinaryOperator(::And, stacks)
                    "|" -> buildBinaryOperator(::Or, stacks)
                    "like" -> buildBinaryOperator(::Like, stacks)
                    "Length(" -> Length(listOf(stacks.pop()))
                    "CurrDate(" -> CurrDate(listOf())
                    "CurrDateTime(" -> CurrDateTime(listOf())
                    "DateTimeAdd(" -> DateTimeAdd(getStackList(segment.parametersCount!!, stacks))
                    "DateTimeDiff(" -> DateTimeDiff(getStackList(segment.parametersCount!!, stacks))
                    "DayOfWeek(" -> DayOfWeek(listOf(stacks.pop()))
                    "NewDateTime(" -> NewDateTime(getStackList(segment.parametersCount!!, stacks))
                    "If(" -> If(getStackList(segment.parametersCount!!, stacks))
                    "Concat(" -> Concat(getStackList(segment.parametersCount!!, stacks))
                    "IndexOf(" -> IndexOf(getStackList(segment.parametersCount!!, stacks))
                    "Left(" -> Left(getStackList(segment.parametersCount!!, stacks))
                    "Right(" -> Right(getStackList(segment.parametersCount!!, stacks))
                    "Lower(" -> Lower(listOf(stacks.pop()))
                    "Upper(" -> Upper(listOf(stacks.pop()))
                    "Replace(" -> Replace(getStackList(segment.parametersCount!!, stacks))
                    "Substr(" -> Substr(getStackList(segment.parametersCount!!, stacks))
                    "Split(" -> Split(getStackList(segment.parametersCount!!, stacks))
                    "Trim(" -> Trim(getStackList(segment.parametersCount!!, stacks))
                    "FormatDateTime(" -> FormatDateTime(getStackList(segment.parametersCount!!, stacks))
                    "FormatDecimal(" -> FormatDecimal(getStackList(segment.parametersCount!!, stacks))
                    "FormatPercent(" -> FormatPercent(getStackList(segment.parametersCount!!, stacks))
                    "Abs(" -> Abs(listOf(stacks.pop()))
                    "Ceiling(" -> Ceiling(getStackList(segment.parametersCount!!, stacks))
                    "Floor(" -> Floor(getStackList(segment.parametersCount!!, stacks))
                    "Max(" -> Max(getStackList(segment.parametersCount!!, stacks))
                    "Min(" -> Min(getStackList(segment.parametersCount!!, stacks))
                    "Avg(" -> Avg(getStackList(segment.parametersCount!!, stacks))
                    "Count(" -> Count(getStackList(segment.parametersCount!!, stacks))
                    "Distinct(" -> Distinct(getStackList(segment.parametersCount!!, stacks))
                    "Group_concat(" -> GroupConcat(getStackList(segment.parametersCount!!, stacks))
                    "Sum(" -> Sum(getStackList(segment.parametersCount!!, stacks))
                    "Mod(" -> Mod(getStackList(segment.parametersCount!!, stacks))
                    "Power(" -> Power(getStackList(segment.parametersCount!!, stacks))
                    "RandInteger(" -> RandInteger(getStackList(segment.parametersCount!!, stacks))
                    "Round(" -> Round(getStackList(segment.parametersCount!!, stacks))
                    "Sqrt(" -> Sqrt(listOf(stacks.pop()))
                    "BooleanToInteger(" -> BooleanToInteger(listOf(stacks.pop()))
                    "BooleanToText(" -> BooleanToText(listOf(stacks.pop()))
                    "DateTimeToDate(" -> DateTimeToDate(listOf(stacks.pop()))
                    "DateTimeToText(" -> DateTimeToText(listOf(stacks.pop()))
                    "DateToDateTime(" -> DateToDateTime(listOf(stacks.pop()))
                    "DateToText(" -> DateToText(listOf(stacks.pop()))
                    "DecimalToBoolean(" -> DecimalToBoolean(listOf(stacks.pop()))
                    "DecimalToInteger(" -> DecimalToInteger(listOf(stacks.pop()))
                    "DecimalToText(" -> DecimalToText(listOf(stacks.pop()))
                    "IntegerToBoolean(" -> IntegerToBoolean(listOf(stacks.pop()))
                    "IntegerToDecimal(" -> IntegerToDecimal(listOf(stacks.pop()))
                    "IntegerToText(" -> IntegerToText(listOf(stacks.pop()))
                    "TextToDate(" -> TextToDate(listOf(stacks.pop()))
                    "TextToDateTime(" -> TextToDateTime(listOf(stacks.pop()))
                    "TextToDateTimeValidate(" -> TextToDateTimeValidate(listOf(stacks.pop()))
                    "TextToDateValidate(" -> TextToDateValidate(listOf(stacks.pop()))
                    "TextToDecimal(" -> TextToDecimal(listOf(stacks.pop()))
                    "TextToDecimalValidate(" -> TextToDecimalValidate(listOf(stacks.pop()))
                    "TextToInteger(" -> TextToInteger(listOf(stacks.pop()))
                    "TextToIntegerValidate(" -> TextToIntegerValidate(listOf(stacks.pop()))
                    "Year(" -> Year(listOf(stacks.pop()))
                    "Month(" -> Month(listOf(stacks.pop()))
                    "Day(" -> Day(listOf(stacks.pop()))
                    "Hour(" -> Hour(listOf(stacks.pop()))
                    "Minute(" -> Minute(listOf(stacks.pop()))
                    "Second(" -> Second(listOf(stacks.pop()))
                    "GetUserId(" -> GetUserId(listOf())
                    "IsNull(" -> IsNull(listOf(stacks.pop()))
                    else -> TODO()
                }
            } else {
                // 操作数类型
                operandCreator.create(segment.text)
            }

            stacks.push(expression)
        }

        if (stacks.size > 1) {
            throw ExpressionResolveException()
        }

        return stacks.pop()
    }

    private fun buildBinaryOperator(
        constructor: (Expression, Expression) -> Expression,
        stacks: Stack<Expression>
    ): Expression {
        // LIFO
        val operand2 = stacks.pop()
        val operand1 = stacks.pop()
        return constructor(operand1, operand2)
    }

    private fun getStackList(count: Int, stacks: Stack<Expression>): List<Expression> {
        val stackList: MutableList<Expression> = mutableListOf()
        for (i in 1..count) {
            stackList.add(stacks.pop())
        }
        return stackList.reversed().toList()
    }

}
