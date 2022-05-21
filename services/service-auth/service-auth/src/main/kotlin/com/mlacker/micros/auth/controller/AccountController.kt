package com.mlacker.micros.auth.controller

import com.mlacker.micros.auth.api.model.AccountModel
import com.mlacker.micros.auth.domain.user.AccountService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/accounts")
class AccountController(
    private val service: AccountService
) {
    @GetMapping("{id}")
    fun get(@PathVariable id: Long): AccountModel {
        return service[id]
    }

    @PostMapping
    fun create(@RequestBody model: AccountModel) {
        service.create(model)
    }

    @PutMapping
    fun modify(@RequestBody model: AccountModel) {
        service.modify(model)
    }

    @DeleteMapping("{id}")
    fun delete(@PathVariable id: Long) {
        service.delete(id)
    }
}