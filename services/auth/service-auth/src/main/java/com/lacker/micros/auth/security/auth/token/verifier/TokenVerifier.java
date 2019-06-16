package com.lacker.micros.auth.security.auth.token.verifier;

public interface TokenVerifier {

    boolean verify(String jti);
}
