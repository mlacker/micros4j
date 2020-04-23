package com.lacker.micros.form

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Import

@SpringBootApplication(scanBasePackages = ["com.lacker.micros.form"])
@EnableFeignClients("com.lacker.micros.data.api.client")
@MapperScan("com.lacker.micros.form.repository.mapper")
@Import(
        com.lacker.micros.config.FeignConfig::class,
        com.lacker.micros.config.MapperConfig::class
)
class FormApplication

fun main(args: Array<String>) {
    runApplication<FormApplication>(*args)
}