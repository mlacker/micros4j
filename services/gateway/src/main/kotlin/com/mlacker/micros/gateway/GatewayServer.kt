package com.mlacker.micros.gateway

import com.mlacker.micros.config.properties.TokenProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(TokenProperties::class)
class GatewayServer

fun main(args: Array<String>) {
    runApplication<GatewayServer>(*args)
}