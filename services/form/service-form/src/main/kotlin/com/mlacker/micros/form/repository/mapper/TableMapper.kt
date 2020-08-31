package com.mlacker.micros.form.repository.mapper

import org.apache.ibatis.annotations.Delete
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Select

interface TableMapper {

    @Select("SELECT table_id FROM data_table WHERE form_id = #{formId}")
    fun findByForm(formId: Long): List<Long>

    @Insert("INSERT INTO data_table (form_id, table_id) VALUES (#{formId}, #{tableId}) ")
    fun insert(formId: Long, tableId: Long)

    @Delete("DELETE FROM data_table WHERE form_id = #{formId}")
    fun deleteByForm(formId: Long)
}
