package com.mlacker.micros.expr

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.mlacker.micros.expr.types.*

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "discr", defaultImpl = BasicType::class)
@JsonSubTypes(
    JsonSubTypes.Type(ListType::class),
    JsonSubTypes.Type(RecordType::class),
    JsonSubTypes.Type(EntityType::class),
    JsonSubTypes.Type(EntityIdentityType::class),
    JsonSubTypes.Type(StructureType::class)
)
interface Type
