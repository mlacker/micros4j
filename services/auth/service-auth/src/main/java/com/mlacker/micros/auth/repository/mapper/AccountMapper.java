package com.mlacker.micros.auth.repository.mapper;

import com.mlacker.micros.auth.domain.user.Account;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface AccountMapper {

    @Select("SELECT * FROM account WHERE id = #{id} AND deleted = 0")
    Account find(Long id);

    @Select("SELECT * FROM account WHERE username = #{username}")
    Account findByUsername(String username);

    @Insert("INSERT INTO account (id, name, username, password_hash, enabled, deleted) " +
            "VALUES (#{id}, #{name}, #{username}, #{passwordHash}, #{enabled}, 0)")
    void insert(Account account);

    @Update("UPDATE account SET name = #{name}, password_hash = #{passwordHash}, enabled = #{enabled} WHERE id = #{id}")
    void update(Account account);

    @Delete("UPDATE account SET deleted = 1 WHERE id = #{id}")
    void delete(Long id);
}
