package com.lacker.micros.form.repository

import com.lacker.micros.form.domain.component.Component
import com.lacker.micros.form.domain.component.ContainerComponent
import com.lacker.micros.form.domain.component.FormComponent
import com.lacker.micros.form.domain.component.FormRepository
import com.lacker.micros.form.repository.mapper.ComponentMapper
import org.springframework.stereotype.Repository

@Repository
class FormRepositoryImpl(
        private val mapper: ComponentMapper
) : FormRepository {

    override fun find(id: Long): FormComponent {
        val components = mapper.findByRoot(id)

        for (component in components) {
            if (component is ContainerComponent) {
                component.children.addAll(components.filter { it.parentId == component.id })
            }
        }

        return components.first() as FormComponent
    }

    override fun save(entity: FormComponent) {
        val components = mutableListOf<Component>()

        getComponents(components, entity)

        delete(entity.id)
        components.forEach { mapper.insert(it) }
    }

    override fun delete(id: Long) = mapper.deleteByRoot(id)

    private fun getComponents(components: MutableList<Component>, component: Component) {
        components.add(component)
        if (component is ContainerComponent)
            for (child in component.children) {
                getComponents(components, child)
            }
    }
}