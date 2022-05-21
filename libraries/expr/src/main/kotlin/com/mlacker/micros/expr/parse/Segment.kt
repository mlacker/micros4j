package com.mlacker.micros.expr.parse

class Segment(val text: String, private val type: SegmentType) {

    enum class SegmentType {
        Operation, Operand, Function
    }

    companion object Factory {
        fun create(text: String): Segment {
            val type = when {
                Regex("""(\.|,|\(|\)|\[\d+]|!|\*|/|%|\+|-|<=?|>=?|==|!=|&|\||like)""").matches(text) -> SegmentType.Operation
                Regex("""\w+\(""").matches(text) -> SegmentType.Function
                else -> SegmentType.Operand
            }

            return Segment(text, type)
        }
    }

    var parametersCount: Int? = null

    fun isOperator(): Boolean = type == SegmentType.Operation

    fun isFunction(): Boolean = type == SegmentType.Function

    fun isOpeningParenthesis(): Boolean = text.endsWith('(')

    fun isClosingParenthesis(): Boolean = text == ")"

    fun isComma(): Boolean = text == ","

    fun precedenceOf(): Int {
        return when (text) {
            "(", "." -> 1
            "!" -> 2
            "*", "/", "%" -> 3
            "+", "-" -> 4
            "<", "<=", ">", ">=" -> 6
            "==", "!=" -> 7
            "like" -> 9
            "&" -> 11
            "|" -> 12
            ")", "," -> 20
            else -> when {
                this.isFunction() -> 1
                text.startsWith("[") -> 1
                else -> TODO()
            }
        }
    }

    override fun toString(): String = text
}
