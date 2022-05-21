package com.mlacker.micros.auth.repository

import com.mlacker.micros.auth.domain.user.Account
import com.mlacker.micros.auth.domain.user.AccountRepository
import com.mlacker.micros.auth.repository.mapper.AccountMapper
import com.mlacker.micros.domain.exception.NotFoundAppException
import org.springframework.stereotype.Repository

@Repository
class AccountRepositoryImpl(private val mapper: AccountMapper) : AccountRepository {

    override fun findByUsername(username: String): Account? {
        return mapper.findByUsername(username)
    }

    override fun delete(account: Account) {
        mapper.delete(account.id)
    }

    override fun find(id: Long): Account {
        return mapper.find(id) ?: throw NotFoundAppException()
    }

    override fun create(account: Account) {
        mapper.insert(account)
    }

    override fun save(entity: Account) {
        mapper.update(entity)
    }
}