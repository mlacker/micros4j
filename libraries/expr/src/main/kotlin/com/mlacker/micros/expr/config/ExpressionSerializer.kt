package com.mlacker.micros.expr.config

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import com.mlacker.micros.expr.Expression

class ExpressionSerializer : JsonSerializer<Expression>() {

    override fun serialize(expression: Expression, jsonGenerator: JsonGenerator, serializerProvider: SerializerProvider) {
        jsonGenerator.writeString(expression.toString())
    }
}
