package com.mlacker.micros.auth.domain.user

import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.annotation.Output
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Component

@Component
@EnableBinding(AccountPublisher.Binder::class)
class AccountPublisher(private val binder: Binder) {

    fun register(account: Account) {
        val message = MessageBuilder
            .withPayload(account.id)
            .build()

        binder.register().send(message)
    }

    interface Binder {
        @Output("accounts-register")
        fun register(): MessageChannel
    }
}