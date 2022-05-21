package com.mlacker.micros.domain.repository

import com.mlacker.micros.domain.entity.AggregateRoot

interface Repository<TEntity : AggregateRoot> {

    fun find(id: Long): TEntity

    fun save(entity: TEntity)
}