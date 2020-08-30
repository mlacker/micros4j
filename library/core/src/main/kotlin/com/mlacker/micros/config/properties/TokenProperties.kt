package com.mlacker.micros.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("micros.security.token")
data class TokenProperties (
    var signingKey: String,
    var issuer: String,
    var expires: Int,
    var skipPaths: MutableList<String> = mutableListOf()
)