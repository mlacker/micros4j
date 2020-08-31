package com.mlacker.micros.form.domain.define

import com.mlacker.micros.domain.entity.PaginatedFilter
import com.mlacker.micros.domain.repository.Repository

interface DefineRepository: Repository<Define> {

    fun findByFilter(filter: PaginatedFilter): List<Define>

    fun findOrderByIdDesc(): List<Define>

    fun existsByIdNotAndName(id: Long, name: String): Boolean

    fun delete(id: Long)
}