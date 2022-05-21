package com.mlacker.micros.expr.config

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.jsontype.TypeSerializer
import com.mlacker.micros.expr.types.BasicType

class BasicTypeSerializer : JsonSerializer<BasicType>() {

    override fun serialize(type: BasicType, generator: JsonGenerator, serializers: SerializerProvider) {
        generator.writeString(type.name)
    }

    override fun serializeWithType(type: BasicType, generator: JsonGenerator, serializers: SerializerProvider, serializer: TypeSerializer) {
        generator.writeString(type.name)
    }
}
