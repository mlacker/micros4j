package com.mlacker.micros.expression.parse

internal class RegexCharacterSplitter : CharacterSplitter {

    private val regex = Regex("""
            (?:\s+|,)
            (?:\w+\()
            (?:'.*?')
            (?:-?\d+(?:\.\d+)?)
            (?:true|false)
            (?:\(|\)|\*|/|%|\+|-|<=?|>=?|==|!=?|&|\|)
            """.trimIndent().replace('\n', '|'))

    override fun split(text: String): List<Segment> {
        var pos = 0
        var lastComma = false
        var result = regex.find(text)
        val segments = ArrayList<Segment>()
        while (result != null) {
            if (pos != result.range.first) {
                throw ParseException(text, pos)
            }

            pos = result.range.last + 1

            if (!result.value.isBlank()) {
                if (result.value == ",") {
                    if (lastComma) {
                        throw ParseException(text, pos)
                    } else {
                        lastComma = true
                    }
                } else {
                    lastComma = false

                    segments.add(Segment.create(result.value))
                }
            }

            result = result.next()
        }

        if (pos != text.length) {
            throw ParseException(text, pos)
        }

        return segments
    }
}