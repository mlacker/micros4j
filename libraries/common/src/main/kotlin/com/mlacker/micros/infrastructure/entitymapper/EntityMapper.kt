package com.mlacker.micros.infrastructure.entitymapper

import kotlin.reflect.KClass

interface EntityMapper {

    fun <S, D> map(source: S, target: D)

    fun <S, D : Any> map(source: S, type: KClass<D>): D
}
