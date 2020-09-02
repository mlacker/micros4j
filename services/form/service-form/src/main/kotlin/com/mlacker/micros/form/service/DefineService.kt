package com.mlacker.micros.form.service

import com.mlacker.micros.domain.entity.PaginatedFilter
import com.mlacker.micros.entitymapper.EntityMapper
import com.mlacker.micros.form.api.model.define.DefineModel
import com.mlacker.micros.form.domain.define.Define
import com.mlacker.micros.form.domain.define.DefineRepository
import org.springframework.cache.annotation.CacheConfig
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@CacheConfig(cacheNames = ["form.defines"])
class DefineService(
        private val formService: FormService,
        private val repo: DefineRepository,
        private val mapper: EntityMapper
) {

    fun findByFilter(filter: PaginatedFilter): List<Define> {
        return repo.findByFilter(filter)
    }

    fun findAll(): List<Define> {
        return repo.findOrderByIdDesc()
    }

    @Cacheable(key = "#id")
    fun find(id: Long): DefineModel {
        val define = repo.find(id)
        val formModel = formService.find(define.formId)

        return DefineModel(define.id, define.name, formModel)
    }

    @CacheEvict(key = "#model.id")
    @Transactional
    fun save(model: DefineModel) {
        model.form.id = formService.save(model.form)

        val define = mapper.map(model, Define::class)

        repo.save(define)
    }

    @CacheEvict(key = "#id")
    @Transactional
    fun delete(id: Long) {
        val define = repo.find(id)

        formService.delete(define.formId)
        repo.delete(id)
    }
}

