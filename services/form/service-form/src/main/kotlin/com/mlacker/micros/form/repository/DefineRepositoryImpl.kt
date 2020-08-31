package com.mlacker.micros.form.repository

import com.mlacker.micros.domain.entity.PaginatedFilter
import com.mlacker.micros.domain.exception.NotFoundAppException
import com.mlacker.micros.form.domain.define.Define
import com.mlacker.micros.form.domain.define.DefineRepository
import com.mlacker.micros.form.repository.mapper.DefineMapper
import org.springframework.stereotype.Repository

@Repository
class DefineRepositoryImpl(private val mapper: DefineMapper) : DefineRepository {

    override fun find(id: Long) = mapper.find(id) ?: throw NotFoundAppException()

    override fun save(entity: Define) =
            if (mapper.find(entity.id) == null) mapper.insert(entity)
            else mapper.update(entity)

    override fun delete(id: Long) = mapper.delete(id)

    override fun findByFilter(filter: PaginatedFilter) = mapper.findByFilter(filter)

    override fun findOrderByIdDesc() = mapper.findOrderByIdDesc()

    override fun existsByIdNotAndName(id: Long, name: String) = mapper.countByIdNotAndName(id, name) > 0
}