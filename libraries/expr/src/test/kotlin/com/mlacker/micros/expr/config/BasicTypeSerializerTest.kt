package com.mlacker.micros.expr.config

import com.fasterxml.jackson.core.Version
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.mlacker.micros.expr.Type
import com.mlacker.micros.expr.types.BasicType
import com.mlacker.micros.expr.types.EntityType
import com.mlacker.micros.expr.types.ListType
import com.mlacker.micros.expr.types.RecordType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class BasicTypeSerializerTest {

    private lateinit var mapper: ObjectMapper

    @BeforeEach
    fun setup() {
        val module = SimpleModule("TypeSerializer", Version.unknownVersion()).also {
            it.addSerializer(BasicType::class.java, BasicTypeSerializer())
        }
        this.mapper = ObjectMapper()
            .registerModule(module)
    }

    @Test
    fun `basic type`() {
        val typeMaps = mapOf(
            BasicType.Text to "Text",
            BasicType.Integer to "Integer",
            BasicType.Decimal to "Decimal",
            BasicType.Boolean to "Boolean",
            BasicType.DateTime to "DateTime",
            BasicType.Date to "Date",
            BasicType.BinaryData to "BinaryData",
        )

        for (typeMap in typeMaps) {
            assertEquals(typeMap.key, basicType(typeMap.value))
            assertEquals(""""${typeMap.value}"""", mapper.writeValueAsString(typeMap.key))
        }
    }

    @Test
    fun `entity type`() {
        val dataType = objectType("""{"discr":"EntityType","entityId":1111}""")

        if (dataType is EntityType) {
            assertEquals(1111, dataType.entityId)
        } else {
            fail()
        }

        assertEquals("""{"discr":"EntityType","entityId":1111}""", mapper.writeValueAsString(dataType))
    }

    @Test
    fun `list type`() {
        val dataType = objectType("""{"discr":"ListType","innerType":"Text"}""")

        if (dataType is ListType) {
            assertEquals(BasicType.Text, dataType.innerType)
        } else {
            fail()
        }

        assertEquals("""{"discr":"ListType","innerType":"Text"}""", mapper.writeValueAsString(dataType))
    }

    @Test
    fun `record type`() {
        val dataType = objectType("""{"discr":"RecordType","properties":[]}""")

        if (dataType is RecordType) {
            assertEquals(0, dataType.properties.size)
        } else {
            fail()
        }

        assertEquals(
            """{"discr":"RecordType","name":"_0","id":0,"properties":[]}""",
            mapper.writeValueAsString(dataType)
        )
    }

    private fun basicType(dataType: String): Type {
        val content = """{ "dataType": "$dataType" }"""

        return mapper.readValue(content, Foo::class.java).dataType
    }

    private fun objectType(dataType: String): Type {
        return mapper.readValue(dataType, Type::class.java)
    }

    class Foo {
        lateinit var dataType: Type
    }
}
