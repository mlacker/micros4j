package com.mlacker.micros.auth.controller

import com.mlacker.micros.auth.api.client.AuthClient
import com.mlacker.micros.auth.domain.user.AccountService
import com.mlacker.micros.auth.model.user.LoginModel
import com.mlacker.micros.domain.context.Principal
import com.mlacker.micros.infrastructure.context.PrincipalHolder
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val service: AccountService
) : AuthClient {

    override fun currentUser(): ResponseEntity<Principal> {
        if (!PrincipalHolder.hasContext()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }

        return ResponseEntity.ok(PrincipalHolder.context)
    }

    @PostMapping("/login")
    fun login(@RequestBody model: LoginModel): ResponseEntity<String> {
        val token = service.login(model)
        return ResponseEntity.ok(token)
    }
}