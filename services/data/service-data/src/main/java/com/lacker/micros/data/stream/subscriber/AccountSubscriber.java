package com.lacker.micros.data.stream.subscriber;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(AccountSubscriber.Binder.class)
public class AccountSubscriber {

    private final Logger logger = LoggerFactory.getLogger(AccountSubscriber.class);

    @StreamListener(Binder.INPUT)
    public void processAccount(String account) {
        logger.info(account);
    }

    // binder
    interface Binder {

        // channel name
        String INPUT = "createAccount";

        // input channel, if a name is not provided, the name of the method is used.
        @Input(Binder.INPUT)
        SubscribableChannel createAccount();
    }
}
