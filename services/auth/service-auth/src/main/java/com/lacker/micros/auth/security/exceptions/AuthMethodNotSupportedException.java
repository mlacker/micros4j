package com.lacker.micros.auth.security.exceptions;

import org.springframework.security.core.AuthenticationException;

public class AuthMethodNotSupportedException extends AuthenticationException {

    public AuthMethodNotSupportedException(String message) {
        super(message);
    }
}
