package com.lacker.micros.auth.security.auth.token.extractor;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class HeaderTokenExtractor implements TokenExtractor {

    private final static String HEADER_PREFIX = "Bearer ";

    @Override
    public String extract(String payload) {
        if (!StringUtils.hasText(payload)) {
            throw new AuthenticationServiceException("Authorization header cannot be empty!");
        }
        if (payload.length() < HEADER_PREFIX.length()) {
            throw new AuthenticationServiceException("Invalid authorization header size.");
        }
        return payload.substring(HEADER_PREFIX.length(), payload.length());
    }
}
