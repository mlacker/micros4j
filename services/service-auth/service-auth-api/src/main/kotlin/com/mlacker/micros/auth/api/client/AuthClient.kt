package com.mlacker.micros.auth.api.client

import com.mlacker.micros.domain.context.Principal
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping

@FeignClient("service-auth/auth")
interface AuthClient {

    @GetMapping("/current-user")
    fun currentUser(): ResponseEntity<Principal>
}