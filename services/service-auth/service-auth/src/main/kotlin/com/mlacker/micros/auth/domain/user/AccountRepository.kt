package com.mlacker.micros.auth.domain.user

import com.mlacker.micros.domain.repository.Repository

interface AccountRepository : Repository<Account> {

    fun findByUsername(username: String): Account?

    fun create(account: Account)

    fun delete(account: Account)
}