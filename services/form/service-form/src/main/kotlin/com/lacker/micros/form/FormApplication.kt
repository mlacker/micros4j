package com.lacker.micros.form

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication(scanBasePackages = ["com.lacker.micros"])
@EnableFeignClients("com.lacker.micros.data.api.client")
class FormApplication

fun main(args: Array<String>) {
    runApplication<FormApplication>(*args)
}