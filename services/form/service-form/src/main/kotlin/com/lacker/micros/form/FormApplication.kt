package com.lacker.micros.form

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication(scanBasePackages = ["com.lacker.micros"])
@EnableFeignClients("com.lacker.micros.data.api.client")
@MapperScan("com.lacker.micros.form.repository.mapper")
class FormApplication

fun main(args: Array<String>) {
    runApplication<FormApplication>(*args)
}