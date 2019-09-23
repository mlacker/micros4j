package com.lacker.micros.domain.infrastructure.context;

import com.lacker.micros.domain.entity.Identity;

public class CurrentUser {

    private static ThreadLocal<Identity> threadLocal = new ThreadLocal<>();

    public static Identity get() {
        return threadLocal.get();
    }

    public static void set(Identity value) {
        threadLocal.set(value);
    }

    public static void remove() {
        threadLocal.remove();
    }
}
