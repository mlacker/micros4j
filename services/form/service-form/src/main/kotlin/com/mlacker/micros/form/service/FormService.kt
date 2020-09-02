package com.mlacker.micros.form.service

import com.mlacker.micros.entitymapper.EntityMapper
import com.mlacker.micros.entitymapper.IdAllocator
import com.mlacker.micros.form.api.model.component.ComponentModel
import com.mlacker.micros.form.api.model.component.FormModel
import com.mlacker.micros.form.domain.component.Component
import com.mlacker.micros.form.domain.component.ContainerComponent
import com.mlacker.micros.form.domain.component.FormComponent
import com.mlacker.micros.form.domain.component.FormRepository
import org.springframework.cache.annotation.CacheConfig
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@CacheConfig(cacheNames = ["form.forms"])
class FormService(
        private val repo: FormRepository,
        private val mapper: EntityMapper
) {

    @Cacheable(key = "#id")
    fun find(id: Long): FormModel {
        val form = repo.find(id)

        return mapper.map(form, FormModel::class)
    }

    @CacheEvict(key = "#model.id")
    @Transactional
    fun save(model: FormModel): Long {
        val idAllocator = IdAllocator()
        val form = mapComponent(model, idAllocator) as FormComponent

        form.schema.tables.addAll(model.schema.tables.map { idAllocator.requestOrGet(it.id) })
        form.schema.relations.addAll(model.schema.relations.map { idAllocator.requestOrGet(it.id) })

        repo.save(form)

        return form.id
    }

    @CacheEvict(key = "#id")
    fun delete(id: Long) {
        repo.delete(id)
    }

    private fun mapComponent(model: ComponentModel, idAllocator: IdAllocator): Component {
        val id = idAllocator.requestOrGet(model.id)
        return when (model.type) {
            "ms-form" -> ::FormComponent
            "ms-table" -> ::ContainerComponent
            else -> ::Component
        }.call(id, model.name, model.type, model.properties).also {
            if (it is ContainerComponent && model.children != null)
                it.children.addAll(model.children!!.map { child -> mapComponent(child, idAllocator) })
        }
    }
}

