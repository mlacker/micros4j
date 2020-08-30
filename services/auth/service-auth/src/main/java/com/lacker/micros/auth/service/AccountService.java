package com.lacker.micros.auth.service;

import com.lacker.micros.auth.api.model.AccountModel;
import com.lacker.micros.auth.domain.user.Account;
import com.lacker.micros.auth.domain.user.AccountRepository;
import com.lacker.micros.auth.model.user.LoginModel;
import com.lacker.micros.auth.service.token.TokenFactory;
import com.lacker.micros.auth.stream.publisher.AccountPublisher;
import com.mlacker.micros.domain.exception.InvalidOperationAppException;
import com.mlacker.micros.domain.exception.InvalidParameterAppException;
import com.mlacker.micros.domain.exception.NotFoundAppException;
import com.mlacker.micros.utils.encrypt.Md5Utils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class AccountService {

    private final AccountRepository repo;
    private final AccountPublisher publisher;
    private final TokenFactory tokenFactory;
    private final Md5Utils passwordEncoder;
    private final ModelMapper mapper;

    public AccountService(AccountRepository repo, AccountPublisher publisher, TokenFactory tokenFactory, ModelMapper mapper) {
        this.repo = repo;
        this.publisher = publisher;
        this.tokenFactory = tokenFactory;
        this.mapper = mapper;
        this.passwordEncoder = Md5Utils.X;
    }

    public String login(LoginModel model) {
        if (StringUtils.isEmpty(model.getUsername()) || StringUtils.isEmpty(model.getPassword())) {
            throw new IllegalArgumentException("Username or password not provided");
        }

        Account account = repo.findByUsername(model.getUsername())
                .orElseThrow(() -> new NotFoundAppException("User not found"));

        if (!account.isEnabled()) {
            throw new InvalidOperationAppException("User has been banned");
        }

        if (!passwordEncoder.verify(model.getPassword(), account.getPasswordHash())) {
            throw new InvalidParameterAppException("Authentication failed. Username or password not valid.");
        }

        return tokenFactory.createToken(account);
    }

    public AccountModel get(Long id) {
        Account account = find(id);
        return mapper.map(account, AccountModel.class);
    }

    public void create(AccountModel model) {
        Account account = mapper.map(model, Account.class);
        String defaultPasswordHash = passwordEncoder.computeHash(Account.DEFAULT_PASSWORD);
        account.setPasswordHash(defaultPasswordHash);
        repo.create(account);
        publisher.create(account);
    }

    public void modify(AccountModel model) {
        Account account = find(model.getId());
        account.setName(model.getName());
        save(account);
    }

    public void delete(Long id) {
        Account account = find(id);
        repo.delete(account);
        publisher.delete(account);
    }

    public void resetPassword(Long id) {
        Account account = find(id);
        String defaultPasswordHash = passwordEncoder.computeHash(Account.DEFAULT_PASSWORD);
        account.setPasswordHash(defaultPasswordHash);
        save(account);
    }

    public void enable(Long id) {
        Account account = find(id);
        account.enable();
        save(account);
    }

    public void disable(Long id) {
        Account account = find(id);
        account.disable();
        save(account);
    }

    private Account find(Long id) {
        try{
            return repo.find(id);
        } catch (NotFoundAppException ex) {
            throw new NotFoundAppException("User not found");
        }
    }

    private void save(Account account) {
        repo.save(account);
        publisher.update(account);
    }
}
