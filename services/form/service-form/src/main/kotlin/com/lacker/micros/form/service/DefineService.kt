package com.lacker.micros.form.service

import com.lacker.micros.domain.entity.PaginatedFilter
import com.lacker.micros.form.api.model.define.DefineModel
import com.lacker.micros.form.domain.define.Define
import com.lacker.micros.form.domain.define.DefineRepository
import org.springframework.cache.annotation.CacheConfig
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@CacheConfig(cacheNames = ["form-defines"])
class DefineService(
        private val repo: DefineRepository,
        private val formService: FormService
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
    @Transactional(rollbackFor = [Exception::class])
    fun save(model: DefineModel) {
        val define = Define(model.name, model.form.id)

        formService.save(model.form)
        repo.save(define)
    }

    @CacheEvict(key = "#id")
    @Transactional(rollbackFor = [Exception::class])
    fun delete(id: Long) {
        val define = repo.find(id)

        formService.delete(define.formId)
        repo.delete(id)
    }
}

