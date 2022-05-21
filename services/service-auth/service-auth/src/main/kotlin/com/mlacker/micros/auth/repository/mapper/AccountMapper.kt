package com.mlacker.micros.auth.repository.mapper

import com.mlacker.micros.auth.domain.user.Account
import org.apache.ibatis.annotations.Delete
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Select
import org.apache.ibatis.annotations.Update

interface AccountMapper {
    @Select("SELECT * FROM account WHERE id = #{id} AND deleted = 0")
    fun find(id: Long?): Account?

    @Select("SELECT * FROM account WHERE username = #{username}")
    fun findByUsername(username: String?): Account?

    @Insert(
        "INSERT INTO account (id, name, username, password_hash, enabled, creation_time, deleted) " +
                "VALUES (#{id}, #{name}, #{username}, #{passwordHash}, #{enabled}, now(), 0)"
    )
    fun insert(account: Account?)

    @Update("UPDATE account SET name = #{name}, password_hash = #{passwordHash}, enabled = #{enabled} WHERE id = #{id}")
    fun update(account: Account?)

    @Delete("UPDATE account SET deleted = 1 WHERE id = #{id}")
    fun delete(id: Long?)
}