package com.mlacker.micros.form.repository.mapper

import org.apache.ibatis.annotations.Delete
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Select

interface RelationMapper {

    @Select("SELECT relation_id FROM data_relation WHERE form_id = #{formId}")
    fun findByForm(formId: Long): List<Long>

    @Insert("INSERT INTO data_relation (form_id, relation_id) VALUES (#{formId}, #{relationId}) ")
    fun insert(formId: Long, relationId: Long)

    @Delete("DELETE FROM data_relation WHERE form_id = #{formId}")
    fun deleteByForm(formId: Long)
}
