package com.mlacker.micros.auth.repository;

import com.mlacker.micros.auth.domain.user.Account;
import com.mlacker.micros.auth.domain.user.AccountRepository;
import com.mlacker.micros.auth.repository.mapper.AccountMapper;
import com.mlacker.micros.domain.exception.NotFoundAppException;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

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

    @NotNull
    @Override
    public Account find(long id) {
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
