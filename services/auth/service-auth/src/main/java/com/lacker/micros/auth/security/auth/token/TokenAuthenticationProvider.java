package com.lacker.micros.auth.security.auth.token;

import com.lacker.micros.auth.security.auth.AuthenticationToken;
import com.lacker.micros.auth.security.config.TokenProperties;
import com.lacker.micros.auth.security.model.token.RawAccessToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.modelmapper.TypeToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TokenAuthenticationProvider implements AuthenticationProvider {

    private final TokenProperties properties;

    public TokenAuthenticationProvider(TokenProperties properties) {
        this.properties = properties;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        RawAccessToken rawAccessToken = (RawAccessToken) authentication.getCredentials();

        Jws<Claims> jwtClaims = rawAccessToken.parseClaims(properties.getSigningKey());
        String subject = jwtClaims.getBody().getSubject();
        List<String> scopes = jwtClaims.getBody().get("scopes", new TypeToken<List<String>>() {
        }.getRawType());
        if (scopes == null) {
            scopes = new ArrayList<>();
        }
        List<SimpleGrantedAuthority> authorities = scopes.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());

        return new AuthenticationToken(subject, authorities);

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return AuthenticationToken.class.isAssignableFrom(authentication);
    }
}
