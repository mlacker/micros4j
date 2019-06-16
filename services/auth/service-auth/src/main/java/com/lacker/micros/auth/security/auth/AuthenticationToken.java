package com.lacker.micros.auth.security.auth;

import com.lacker.micros.auth.security.model.token.RawAccessToken;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class AuthenticationToken extends AbstractAuthenticationToken {

    private RawAccessToken rawAccessToken;
    private String username;

    public AuthenticationToken(RawAccessToken unsafeToken) {
        super(null);
        super.setAuthenticated(false);
        this.rawAccessToken = unsafeToken;
    }

    public AuthenticationToken(String username, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        super.eraseCredentials();
        super.setAuthenticated(true);
        this.username = username;
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        if (authenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }
        super.setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return rawAccessToken;
    }

    @Override
    public Object getPrincipal() {
        return username;
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        this.rawAccessToken = null;
    }
}
