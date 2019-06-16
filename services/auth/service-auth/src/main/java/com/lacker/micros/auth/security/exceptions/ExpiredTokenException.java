package com.lacker.micros.auth.security.exceptions;

import com.lacker.micros.auth.security.model.token.Token;
import org.springframework.security.core.AuthenticationException;

public class ExpiredTokenException extends AuthenticationException {

    private final Token token;

    public ExpiredTokenException(Token token, String message, Throwable throwable) {
        super(message, throwable);
        this.token = token;
    }

    public Token getToken() {
        return token;
    }
}
