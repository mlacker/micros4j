package com.lacker.micros.auth.domain.user;

import com.lacker.micros.domain.entity.AggregateRoot;
import com.lacker.micros.domain.entity.EntityImpl;
import com.lacker.micros.utils.id.SequenceIdGenerator;

public class Account extends EntityImpl implements AggregateRoot{

    public final static String DEFAULT_PASSWORD = "s123456";

    private String name;
    private String username;
    private String passwordHash;
    private boolean enabled;

    private Account() {
    }

    public Account(String name, String username) {
        this.name = name;
        this.username = username;
        this.enabled = true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void enable() {
        this.enabled = true;
    }

    public void disable() {
        this.enabled = false;
    }
}
