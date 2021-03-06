package com.mlacker.micros.auth.service.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.mlacker.micros.auth.domain.user.Account;
import com.mlacker.micros.config.properties.TokenProperties;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import org.springframework.stereotype.Component;

@Component
public class TokenFactory {

    private final TokenProperties properties;

    public TokenFactory(TokenProperties properties) {
        this.properties = properties;
    }

    public String createToken(Account account) {
        if (account == null) {
            throw new IllegalArgumentException("Cannot create token without account");
        }

        LocalDateTime currentTime = LocalDateTime.now();

        return JWT.create()
                .withIssuer(properties.getIssuer())
                .withIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
                .withExpiresAt(Date.from(currentTime.plusMinutes(properties.getExpires())
                        .atZone(ZoneId.systemDefault()).toInstant()))
                .withSubject(String.valueOf(account.getId()))
                .withClaim("name", account.getName())
                .withClaim("app", "1138238647")
                .sign(Algorithm.HMAC256(properties.getSigningKey()));
    }
}
