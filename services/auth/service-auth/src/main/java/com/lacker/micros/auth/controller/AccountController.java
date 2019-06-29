package com.lacker.micros.auth.controller;

import com.lacker.micros.auth.stream.publisher.AccountPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountPublisher accountPublisher;

    @Autowired
    public AccountController(AccountPublisher accountPublisher) {
        this.accountPublisher = accountPublisher;
    }

    @GetMapping("create")
    public void create() {
        accountPublisher.createAccount();
    }
}
