package com.mlacker.micros.config

import com.mlacker.micros.infrastructure.context.PrincipalAwareFilter
import com.mlacker.micros.infrastructure.context.PrincipalInjectInterceptor
import feign.RequestInterceptor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.filter.OncePerRequestFilter

@Configuration
class PrincipalConfig {

    @Bean
    fun principalAwareFilter(): OncePerRequestFilter {
        return PrincipalAwareFilter()
    }

    @Bean
    fun principalInjectInterceptor(): RequestInterceptor {
        return PrincipalInjectInterceptor()
    }
}