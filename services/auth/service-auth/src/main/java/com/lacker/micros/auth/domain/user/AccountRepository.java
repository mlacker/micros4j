package com.lacker.micros.auth.domain.user;

import com.mlacker.micros.domain.repository.Repository;

import java.util.Optional;

public interface AccountRepository extends Repository<Account> {

    Optional<Account> findByUsername(String username);

    void create(Account account);

    void delete(Account account);
}
