package com.lacker.micros.auth.domain;

import com.lacker.micros.domain.entity.AggregateRoot;
import com.lacker.micros.domain.entity.EntityImpl;

public class User extends EntityImpl implements AggregateRoot{

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
