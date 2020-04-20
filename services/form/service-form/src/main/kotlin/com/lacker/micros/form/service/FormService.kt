package com.lacker.micros.form.service

import com.lacker.micros.form.api.model.component.FormModel
import org.springframework.cache.annotation.CacheConfig
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@CacheConfig(cacheNames = ["form-forms"])
class FormService {

    @Cacheable(key = "#id")
    fun find(id: Long): FormModel {
        return FormModel(id)
    }

    @CacheEvict(key = "#model.id")
    @Transactional(rollbackFor = [Exception::class])
    fun save(form: FormModel) {
    }

    @CacheEvict(key = "#id")
    fun delete(id: Long) {
    }
}