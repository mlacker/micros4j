package com.mlacker.micros.registry

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer

@SpringBootApplication
@EnableEurekaServer
class RegistryServer

fun main(args: Array<String>) {
    runApplication<RegistryServer>(*args)
}