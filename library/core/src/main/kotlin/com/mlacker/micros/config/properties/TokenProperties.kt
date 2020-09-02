package com.mlacker.micros.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("micros.security.token")
class TokenProperties {
    lateinit var signingKey: String
    lateinit var issuer: String
    var expires: Int = 30
    var skipPaths: List<String> = emptyList()
}