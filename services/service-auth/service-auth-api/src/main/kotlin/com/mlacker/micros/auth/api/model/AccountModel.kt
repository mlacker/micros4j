package com.mlacker.micros.auth.api.model

data class AccountModel(
    val id: Long,
    val name: String,
    val username: String,
    val isEnabled: Boolean = false
)