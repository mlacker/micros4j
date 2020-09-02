package com.mlacker.micros.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("micros.security.token")
class TokenProperties {
    var signingKey: String? = null
    var issuer: String? = null
    var expires: Int? = null
    var skipPaths: MutableList<String> = mutableListOf()
}