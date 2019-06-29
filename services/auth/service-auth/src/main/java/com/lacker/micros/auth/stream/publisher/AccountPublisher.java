package com.lacker.micros.auth.stream.publisher;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(AccountPublisher.Binder.class)
public class AccountPublisher {

    private final Binder binder;

    public AccountPublisher(AccountPublisher.Binder binder) {
        this.binder = binder;
    }

    public void createAccount() {
        Message<String> message = MessageBuilder
                .withPayload("Hello")
                .build();

        binder.createAccount().send(message);
    }

    interface Binder {

        @Output
        MessageChannel createAccount();
    }
}
