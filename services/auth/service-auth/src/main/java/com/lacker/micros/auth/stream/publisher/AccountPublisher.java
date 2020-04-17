package com.lacker.micros.auth.stream.publisher;

import com.lacker.micros.auth.api.model.AccountModel;
import com.lacker.micros.auth.domain.user.Account;
import org.modelmapper.ModelMapper;
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
    private final ModelMapper mapper;

    public AccountPublisher(Binder binder, ModelMapper mapper) {
        this.binder = binder;
        this.mapper = mapper;
    }

    public void create(Account account) {
        AccountModel model = mapper.map(account, AccountModel.class);
        Message<AccountModel> message = MessageBuilder
                .withPayload(model)
                .build();

        binder.createAccount().send(message);
    }

    public void update(Account account) {
        AccountModel model = mapper.map(account, AccountModel.class);
        Message<AccountModel> message = MessageBuilder
                .withPayload(model)
                .build();

        binder.updateAccount().send(message);
    }

    public void delete(Account account) {
        Message<Long> message = MessageBuilder
                .withPayload(account.getId())
                .build();

        binder.deleteAccount().send(message);
    }

    interface Binder {

        @Output
        MessageChannel createAccount();
        @Output
        MessageChannel updateAccount();
        @Output
        MessageChannel deleteAccount();
    }
}
