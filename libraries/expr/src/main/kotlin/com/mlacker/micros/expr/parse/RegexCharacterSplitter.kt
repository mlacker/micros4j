package com.mlacker.micros.expr.parse

class RegexCharacterSplitter : CharacterSplitter {

    private val regex = Regex(
        """
        (?:\s+|,)
        (?:\w+\()
        (?:".*?")
        (?:#\d{4}-\d{2}-\d{2}(?: \d{1,2}:\d{1,2}:\d{1,2})?#)
        (?:-?\d+(?:\.\d+)?\b)
        (?:true|false)
        (?:\.|\(|\)|\[\d+\]|\*|/|%|\+|-|<=?|>=?|==|!=?|&|\||like)
        (?:[a-zA-Z_][a-zA-Z_\d]*?\b)
        """.trimIndent().replace('\n', '|')
    )

    override fun split(text: String): List<Segment> {
        var pos = 0
        var lastComma = false
        var result = regex.find(text)
        val segments = ArrayList<Segment>()
        while (result != null) {
            if (pos != result.range.first) {
                throw ParseException(text, pos)
            }

            val value = result.value
            if (value == ",") {
                if (lastComma) {
                    throw ParseException(text, pos)
                }

                lastComma = true
                segments.add(Segment.create(value))
            } else if (value.isNotBlank()) {
                lastComma = false

                if (value.startsWith('-') && value.length > 1
                    && segments.lastOrNull()?.let { (it.isOperator() && !it.isClosingParenthesis()) || it.isFunction() } == false
                ) {
                    segments.add(Segment.create("-"))
                    segments.add(Segment.create(value.substring(1)))
                } else {
                    segments.add(Segment.create(value))
                }
            }

            pos = result.range.last + 1
            result = result.next()
        }

        if (pos != text.length) {
            throw ParseException(text, pos)
        }

        return segments
    }
}
