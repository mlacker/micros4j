package com.lacker.micros.form.service

import com.lacker.micros.form.api.model.component.FormModel
import com.lacker.micros.form.domain.component.FormComponent
import com.lacker.micros.form.domain.component.FormRepository
import org.modelmapper.ModelMapper
import org.springframework.cache.annotation.CacheConfig
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@CacheConfig(cacheNames = ["form-forms"])
class FormService(
        private val repo: FormRepository,
        private val mapper: ModelMapper
) {

    @Cacheable(key = "#id")
    fun find(id: Long): FormModel {
        val form = repo.find(id)

        return mapper.map(form, FormModel::class.java)
    }

    @CacheEvict(key = "#model.id")
    @Transactional(rollbackFor = [Exception::class])
    fun save(model: FormModel) {
        val form = mapper.map(model, FormComponent::class.java)

        repo.save(form)
    }

    @CacheEvict(key = "#id")
    fun delete(id: Long) {
        repo.delete(id)
    }
}