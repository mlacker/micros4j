package com.mlacker.micros.infrastructure.entitymapper

import org.modelmapper.TypeToken
import org.modelmapper.internal.InheritingConfiguration
import org.modelmapper.internal.MappingEngineImpl

class MappingEngineProxy(configuration: InheritingConfiguration?) : MappingEngineImpl(configuration) {
    override fun <S : Any?, D : Any?> map(source: S, sourceType: Class<S>, destination: D, destinationTypeToken: TypeToken<D>, typeMapName: String?): D {
        val context = MappingContextProxy(source, sourceType,
                destination, destinationTypeToken.rawType, destinationTypeToken.type,
                typeMapName, this)

        return map(context)
    }
}