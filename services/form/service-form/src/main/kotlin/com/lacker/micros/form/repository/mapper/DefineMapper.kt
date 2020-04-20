package com.lacker.micros.form.repository.mapper

import com.lacker.micros.domain.entity.PaginatedFilter
import com.lacker.micros.form.domain.define.Define
import org.apache.ibatis.annotations.*

interface DefineMapper {

    @Select("SELECT * FROM define WHERE id = #{id}")
    fun find(id: Long): Define?

    @Insert("INSERT INTO define (id, name, form_id) VALUES (#{id}, #{name}, #{formId})")
    fun insert(entity: Define)

    @Update("UPDATE define SET name = #{name} WHERE id = #{id}")
    fun update(entity: Define)

    @Delete("DELETE define WHERE id = #{id}")
    fun delete(id: Long)

    // TODO searchString
    @Select("SELECT * FROM define ",
            "WHERE (#{filters.searchString} IS NULL OR name LIKE '%#{filters.searchString}%') ",
            "ORDER BY id DESC LIMIT #{start}, #{size}")
    fun findByFilter(filter: PaginatedFilter): List<Define>

    @Select("SELECT * FROM define ORDER BY id DESC")
    fun findOrderByIdDesc(): List<Define>

    @Select("SELECT COUNT(*) FROM define WHERE id <> #{id} AND name = #{name}")
    fun countByIdNotAndName(id: String, name: String): Long
}