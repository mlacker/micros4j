package com.mlacker.micros.auth.domain.user

import com.mlacker.micros.auth.api.model.AccountModel
import com.mlacker.micros.auth.model.user.LoginModel
import com.mlacker.micros.domain.exception.InvalidOperationAppException
import com.mlacker.micros.domain.exception.InvalidParameterAppException
import com.mlacker.micros.domain.exception.NotFoundAppException
import org.modelmapper.ModelMapper
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils

@Service
class AccountService(
    private val repo: AccountRepository,
    private val publisher: AccountPublisher,
    private val tokenFactory: TokenFactory,
    private val passwordEncoder: PasswordEncoder,
    private val mapper: ModelMapper
) {

    fun login(model: LoginModel): String {
        require(StringUtils.hasText(model.username) && StringUtils.hasText(model.password)) { "Username or password not provided" }

        val account = repo.findByUsername(model.username) ?: throw NotFoundAppException("User not found")

        if (!account.isEnabled) {
            throw InvalidOperationAppException("User has been banned")
        }

        if (!passwordEncoder.matches(model.password, account.passwordHash)) {
            throw InvalidParameterAppException("Authentication failed. Username or password not valid.")
        }

        return tokenFactory.createToken(account)
    }

    operator fun get(id: Long): AccountModel {
        val account = find(id)
        return mapper.map(account, AccountModel::class.java)
    }

    fun create(model: AccountModel) {
        val account = mapper.map(model, Account::class.java)
        val defaultPasswordHash = passwordEncoder.encode(Account.DEFAULT_PASSWORD)
        account.passwordHash = defaultPasswordHash
        repo.create(account)
        publisher.register(account)
    }

    fun modify(model: AccountModel) {
        val account = find(model.id)
        account.name = model.name
        repo.save(account)
    }

    fun delete(id: Long) {
        val account = find(id)
        repo.delete(account)
    }

    fun resetPassword(id: Long) {
        val account = find(id)
        val defaultPasswordHash = passwordEncoder.encode(Account.DEFAULT_PASSWORD)
        account.passwordHash = defaultPasswordHash
        repo.save(account)
    }

    fun enable(id: Long) {
        val account = find(id)
        account.enable()
        repo.save(account)
    }

    fun disable(id: Long) {
        val account = find(id)
        account.disable()
        repo.save(account)
    }

    private fun find(id: Long): Account {
        return try {
            repo.find(id)
        } catch (ex: NotFoundAppException) {
            throw NotFoundAppException("User not found")
        }
    }
}