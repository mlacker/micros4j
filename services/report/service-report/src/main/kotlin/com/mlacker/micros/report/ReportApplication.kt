package com.mlacker.micros.report

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Import

@SpringBootApplication(scanBasePackages = [
    "com.mlacker.micros.report",
    "com.mlacker.micros.domain.exception"
])
@EnableFeignClients("com.mlacker.micros.data.api.client")
@MapperScan("com.mlacker.micros.report.repository.mapper")
@Import(
        com.mlacker.micros.config.PrincipalConfig::class,
        com.mlacker.micros.config.FeignConfig::class,
        com.mlacker.micros.config.MapperConfig::class
)
class ReportApplication

fun main(args: Array<String>) {
    runApplication<ReportApplication>(*args)
}