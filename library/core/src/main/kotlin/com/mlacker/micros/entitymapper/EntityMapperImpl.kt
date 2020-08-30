package com.mlacker.micros.entitymapper

import org.modelmapper.ModelMapper
import kotlin.reflect.KClass

class EntityMapperImpl(private val modelMapper: ModelMapper) : EntityMapper {

    override fun <S, D> map(source: S, target: D) {
        modelMapper.map(source, target)
    }

    override fun <S, D : Any> map(source: S, type: KClass<D>): D {
        return modelMapper.map(source, type.java)
    }
}

