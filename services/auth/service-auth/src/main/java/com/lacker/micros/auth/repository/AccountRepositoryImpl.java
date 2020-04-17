package com.lacker.micros.auth.repository;

import com.lacker.micros.auth.domain.user.Account;
import com.lacker.micros.auth.domain.user.AccountRepository;
import com.lacker.micros.auth.repository.mapper.AccountMapper;
import com.lacker.micros.domain.exception.NotFoundAppException;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AccountRepositoryImpl implements AccountRepository {

    private final AccountMapper mapper;

    public AccountRepositoryImpl(AccountMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Optional<Account> findByUsername(String username) {
        return Optional.ofNullable(mapper.findByUsername(username));
    }

    @Override
    public void delete(Account account) {
        mapper.delete(account.getId());
    }

    @Override
    public Account find(@NotNull Long id) {
        Account account = mapper.find(id);

        if (account == null) {
            throw new NotFoundAppException();
        }

        return account;
    }

    @Override
    public void create(Account account) {
        mapper.insert(account);
    }

    @Override
    public void save(@NotNull Account account) {
        mapper.update(account);
    }
}
