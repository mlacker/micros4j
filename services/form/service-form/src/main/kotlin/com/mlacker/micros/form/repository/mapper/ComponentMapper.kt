package com.mlacker.micros.form.repository.mapper

import com.mlacker.micros.form.domain.component.Component
import com.mlacker.micros.form.domain.component.ContainerComponent
import com.mlacker.micros.form.domain.component.FormComponent
import org.apache.ibatis.annotations.*

interface ComponentMapper {

    @ConstructorArgs(
            Arg(column = "id", javaType = Long::class, id = true),
            Arg(column = "name", javaType = String::class),
            Arg(column = "type", javaType = String::class),
            Arg(column = "properties", javaType = String::class)
    )
    @TypeDiscriminator(column = "type", cases = [
        Case(value = "ms-form", type = FormComponent::class),
        Case(value = "ms-table", type = ContainerComponent::class)
    ])
    @Select("SELECT * FROM component WHERE root_id = #{id}")
    fun findByRoot(id: Long): List<Component>

    @Insert("INSERT INTO component (id, root_id, parent_id, name, type, properties) ",
            "VALUES (#{c.id}, #{formId}, #{c.parentId}, #{c.name}, #{c.type}, #{c.properties}) ")
    fun insert(@Param("c") component: Component, @Param("formId") formId: Long)

    @Delete("DELETE FROM component WHERE root_id = #{id}")
    fun deleteByRoot(id: Long)
}
