package com.lacker.micros.auth.security.model.token;

import io.jsonwebtoken.Claims;

public final class AccessToken implements Token {

    private final String token;
    private final Claims claims;

    AccessToken(String token, Claims claims) {
        this.token = token;
        this.claims = claims;
    }

    public String getToken() {
        return token;
    }

    public Claims getClaims() {
        return claims;
    }
}
