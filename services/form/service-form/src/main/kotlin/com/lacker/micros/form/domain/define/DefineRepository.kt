package com.lacker.micros.form.domain.define

import com.lacker.micros.domain.entity.PaginatedFilter
import com.lacker.micros.domain.repository.Repository

interface DefineRepository: Repository<Define> {

    fun findByFilter(filter: PaginatedFilter): List<Define>

    fun findOrderByIdDesc(): List<Define>

    fun existsByIdNotAndName(id: String, name: String): Boolean

    fun delete(id: Long)
}