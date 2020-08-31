package com.mlacker.micros.data.stream.subscriber;

import com.mlacker.micros.auth.api.model.AccountModel;
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

    @StreamListener(Binder.CREATE_ACCOUNT)
    public void createAccount(AccountModel account) {
        loggerAccount(account);
    }

    @StreamListener(Binder.UPDATE_ACCOUNT)
    public void updateAccount(AccountModel account) {
        loggerAccount(account);
    }

    @StreamListener(Binder.DELETE_ACCOUNT)
    public void deleteAccount(String accountId) {
        logger.info(accountId);
    }

    private void loggerAccount(AccountModel account) {
        logger.info("Id: {}, Name: {}, Username: {}, Enabled: {}",
                account.getId(), account.getName(), account.getUsername(), account.isEnabled());
    }

    // binder
    interface Binder {

        // channel name
        String CREATE_ACCOUNT = "createAccount";
        String UPDATE_ACCOUNT = "updateAccount";
        String DELETE_ACCOUNT = "deleteAccount";

        // input channel, if a name is not provided, the name of the method is used.
        @Input(Binder.CREATE_ACCOUNT)
        SubscribableChannel createAccount();
        @Input(Binder.UPDATE_ACCOUNT)
        SubscribableChannel updateAccount();
        @Input(Binder.DELETE_ACCOUNT)
        SubscribableChannel deleteAccount();
    }
}
