package com.mlacker.micros.app

import com.mlacker.micros.config.FeignConfig
import com.mlacker.micros.config.MapperConfig
import com.mlacker.micros.config.PrincipalConfig
import com.mlacker.micros.domain.exception.GlobalExceptionCatcher
import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@SpringBootApplication
@Import(PrincipalConfig::class, FeignConfig::class, MapperConfig::class, GlobalExceptionCatcher::class)
@MapperScan(basePackages = ["com.mlacker.micros.app.repository.mapper"])
class ServiceApp

fun main(args: Array<String>) {
    runApplication<ServiceApp>(*args)
}