package com.mlacker.micros.form

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Import

@SpringBootApplication(scanBasePackages = [
    "com.mlacker.micros.form",
    "com.mlacker.micros.domain.exception"
])
@EnableFeignClients("com.mlacker.micros.data.api.client")
@MapperScan("com.mlacker.micros.form.repository.mapper")
@Import(
        com.mlacker.micros.config.FeignConfig::class,
        com.mlacker.micros.config.MapperConfig::class
)
class FormApplication

fun main(args: Array<String>) {
    runApplication<FormApplication>(*args)
}