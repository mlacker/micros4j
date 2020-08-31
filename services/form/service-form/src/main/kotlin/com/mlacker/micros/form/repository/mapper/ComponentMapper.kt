package com.mlacker.micros.form.repository.mapper

import com.mlacker.micros.form.domain.component.Component
import com.mlacker.micros.form.domain.component.ContainerComponent
import com.mlacker.micros.form.domain.component.FormComponent
import org.apache.ibatis.annotations.*

interface ComponentMapper {

    @TypeDiscriminator(column = "type", cases = [
        Case(value = "ms-form", type = FormComponent::class),
        Case(value = "ms-table", type = ContainerComponent::class)
    ])
    @Select("SELECT * FROM component WHERE root_id = #{id}")
    fun findByRoot(id: Long): List<Component>

    @Insert("INSERT INTO component (id, name, type, properties) ",
            "VALUES (#{id}, #{name}, #{type}, #{properties}) ")
    fun insert(component: Component)

    @Delete("DELETE FROM component WHERE root_id = #{id}")
    fun deleteByRoot(id: Long)
}
