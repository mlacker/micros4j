package com.mlacker.micros.expr.parse

class ParseException(private val expressionText: String, private val position: Int) : Exception() {
    override val message: String
        get() {
            val message = StringBuilder(expressionText.length * 2 + 12)

            message.appendLine()
            message.appendLine(expressionText)
            for (i in 1..position) {
                message.append(' ')
            }
            message.appendLine("↑ (pos $position)")

            return message.toString()
        }
}
