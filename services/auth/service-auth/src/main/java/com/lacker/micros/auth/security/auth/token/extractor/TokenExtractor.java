package com.lacker.micros.auth.security.auth.token.extractor;

public interface TokenExtractor {

    String extract(String payload);
}
