package com.mlacker.micros.expression.parse

class Segment(val text: String, private val type: SegmentType) {

    companion object Factory {
        fun create(text: String): Segment {
            val type = when {
                Regex("""(\(|\)|!|\*|/|%|\+|-|<=?|>=?|==|!=|&|\|)""").matches(text) -> SegmentType.Operation
                Regex("""\w+\(""").matches(text) -> SegmentType.Function
                else -> SegmentType.Operand
            }

            return Segment(text, type)
        }
    }

    fun isOperator(): Boolean = type == SegmentType.Operation

    fun isFunction(): Boolean = type == SegmentType.Function

    fun isOpeningParenthesis(): Boolean = text == "("

    fun isClosingParenthesis(): Boolean = text == ")"

    fun precedenceOf(): Int = when (text) {
        "(" -> 1
        "!" -> 2
        "*", "/", "%" -> 3
        "+", "-" -> 4
        "<", "<=", ">", ">=" -> 6
        "==", "!=" -> 7
        "&" -> 11
        "|" -> 12
        else -> 20
    }
}

enum class SegmentType {
    Operation, Operand, Function
}
