package com.lacker.micros.auth.security.model.token;

import com.lacker.micros.auth.security.exceptions.ExpiredTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.security.authentication.BadCredentialsException;

public class RawAccessToken implements Token {

    private String token;

    public RawAccessToken(String token) {
        this.token = token;
    }

    public Jws<Claims> parseClaims(String signingKey) {
        try {
            return Jwts.parser().setSigningKey(signingKey).parseClaimsJws(this.token);
        } catch (UnsupportedJwtException| MalformedJwtException| SignatureException| IllegalArgumentException ex) {
            throw new BadCredentialsException("Invalid token", ex);
        } catch (ExpiredJwtException ex) {
            throw new ExpiredTokenException(this, "Token expired", ex);
        }
    }

    @Override
    public String getToken() {
        return token;
    }
}
