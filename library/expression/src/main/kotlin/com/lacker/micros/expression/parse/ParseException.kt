package com.lacker.micros.expression.parse

class ParseException(private val expressionText: String, private val position: Int) : Exception() {
    override val message: String?
        get() {
            val message = StringBuilder(expressionText.length * 2 + 12)

            message.appendln(expressionText)
            for (i in 1..position) {
                message.append(' ')
            }
            message.appendln("↑ (pos $position)")

            return message.toString()
        }
}