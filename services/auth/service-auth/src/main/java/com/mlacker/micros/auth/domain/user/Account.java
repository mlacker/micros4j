package com.mlacker.micros.auth.domain.user;

import com.mlacker.micros.domain.entity.AggregateRoot;
import com.mlacker.micros.domain.entity.EntityImpl;

public class Account extends EntityImpl implements AggregateRoot{

    public final static String DEFAULT_PASSWORD = "s123456";

    private String name;
    private String username;
    private String passwordHash;
    private boolean enabled;

    public Account(long id, String name, String username) {
        super(id);
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
