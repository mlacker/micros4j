package com.mlacker.micros.auth.domain.user

import com.mlacker.micros.domain.entity.AggregateRoot
import com.mlacker.micros.domain.entity.EntityImpl

class Account(
    id: Long,
    var name: String,
    val username: String,
    var passwordHash: String
) : EntityImpl(id), AggregateRoot {

    var isEnabled = true
        private set

    fun enable() {
        isEnabled = true
    }

    fun disable() {
        isEnabled = false
    }

    companion object {
        const val DEFAULT_PASSWORD = "s123456"
    }
}