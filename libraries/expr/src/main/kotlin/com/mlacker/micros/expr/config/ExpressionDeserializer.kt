package com.mlacker.micros.expr.config

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.mlacker.micros.expr.Expression
import com.mlacker.micros.expr.parse.ExpressionParser

class ExpressionDeserializer(private val parser: ExpressionParser) : JsonDeserializer<Expression>() {

    override fun deserialize(parser: JsonParser, deserializationContext: DeserializationContext): Expression {
        return this.parser.parse(parser.valueAsString)
    }
}
