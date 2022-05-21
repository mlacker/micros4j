package com.mlacker.micros.auth

import com.mlacker.micros.config.FeignConfig
import com.mlacker.micros.config.MapperConfig
import com.mlacker.micros.config.PrincipalConfig
import com.mlacker.micros.config.properties.TokenProperties
import com.mlacker.micros.domain.exception.GlobalExceptionCatcher
import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@SpringBootApplication
@Import(PrincipalConfig::class, FeignConfig::class, MapperConfig::class, GlobalExceptionCatcher::class)
@MapperScan(basePackages = ["com.mlacker.micros.auth.repository.mapper"])
@EnableConfigurationProperties(TokenProperties::class)
class AuthApplication {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}

fun main(args: Array<String>) {
    runApplication<AuthApplication>(*args)
}
