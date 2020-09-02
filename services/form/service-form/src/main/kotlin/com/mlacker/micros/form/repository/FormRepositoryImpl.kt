package com.mlacker.micros.form.repository

import com.mlacker.micros.form.domain.component.Component
import com.mlacker.micros.form.domain.component.ContainerComponent
import com.mlacker.micros.form.domain.component.FormComponent
import com.mlacker.micros.form.domain.component.FormRepository
import com.mlacker.micros.form.repository.mapper.ComponentMapper
import com.mlacker.micros.form.repository.mapper.RelationMapper
import com.mlacker.micros.form.repository.mapper.TableMapper
import org.springframework.stereotype.Repository

@Repository
class FormRepositoryImpl(
        private val mapper: ComponentMapper,
        private val tableMapper: TableMapper,
        private val relationMapper: RelationMapper
) : FormRepository {

    override fun find(id: Long): FormComponent {
        val components = mapper.findByRoot(id)

        for (component in components) {
            if (component is ContainerComponent) {
                component.children.addAll(components.filter { it.parentId == component.id })
            }
        }

        val form = components.first() as FormComponent

        form.schema.tables.addAll(tableMapper.findByForm(id))
        form.schema.relations.addAll(relationMapper.findByForm(id))

        return form
    }

    override fun save(entity: FormComponent) {
        val components = mutableListOf<Component>()

        getComponents(components, entity)

        delete(entity.id)

        components.forEach { mapper.insert(it, entity.id) }
        entity.schema.tables.forEach { tableMapper.insert(entity.id, it) }
        entity.schema.relations.forEach { relationMapper.insert(entity.id, it) }
    }

    override fun delete(id: Long) {
        tableMapper.deleteByForm(id)
        relationMapper.deleteByForm(id)
        mapper.deleteByRoot(id)
    }

    private fun getComponents(components: MutableList<Component>, component: Component) {
        components.add(component)
        if (component is ContainerComponent)
            for (child in component.children) {
                child.parentId = component.id
                getComponents(components, child)
            }
    }
}