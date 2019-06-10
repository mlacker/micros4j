package com.lacker.micros.config;

import com.lacker.micros.domain.exception.FeignException;
import feign.Util;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class FeignConfig {

    @Bean
    public ErrorDecoder errorDecoder() {

        return (methodKey, response) -> {
            String message = "";
            try {
                message = Util.toString(response.body().asReader());
            } catch (IOException ignored) {
            }
            return new FeignException(response.status(), message);
        };
    }
}
