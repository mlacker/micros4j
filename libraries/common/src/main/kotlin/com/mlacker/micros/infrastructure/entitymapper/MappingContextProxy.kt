package com.mlacker.micros.infrastructure.entitymapper

import org.modelmapper.internal.MappingContextImpl
import org.modelmapper.internal.MappingEngineImpl
import java.lang.reflect.Type

class MappingContextProxy<S, D>(
        source: S,
        sourceType: Class<S>,
        destination: D,
        destinationType: Class<D>,
        genericDestinationType: Type,
        typeMapName: String?,
        mappingEngine: MappingEngineImpl
) : MappingContextImpl<S, D>(source, sourceType, destination, destinationType, genericDestinationType, typeMapName, mappingEngine) {
    val idAllocator = IdAllocator()
}