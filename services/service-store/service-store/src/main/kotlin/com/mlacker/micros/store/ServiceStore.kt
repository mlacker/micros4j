package com.mlacker.micros.store

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
@MapperScan(basePackages = ["com.mlacker.micros.store.repository.mapper"])
class ServiceStore

fun main(args: Array<String>) {
    runApplication<ServiceStore>(*args)
}