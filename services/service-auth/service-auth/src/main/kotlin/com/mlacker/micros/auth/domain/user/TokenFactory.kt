package com.mlacker.micros.auth.domain.user

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.mlacker.micros.config.properties.TokenProperties
import org.springframework.stereotype.Component
import java.sql.Date
import java.time.LocalDateTime
import java.time.ZoneId

@Component
class TokenFactory(private val properties: TokenProperties) {

    fun createToken(account: Account): String {
        val currentTime = LocalDateTime.now()

        return JWT.create()
            .withIssuer(properties.issuer)
            .withIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
            .withExpiresAt(
                Date.from(
                    currentTime.plusMinutes(properties.expires.toLong())
                        .atZone(ZoneId.systemDefault()).toInstant()
                )
            )
            .withSubject(account.id.toString())
            .withClaim("name", account.name)
            .withClaim("app", "1138238647")
            .sign(Algorithm.HMAC256(properties.signingKey))
    }
}