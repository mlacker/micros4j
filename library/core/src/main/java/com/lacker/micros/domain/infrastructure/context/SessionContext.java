package com.lacker.micros.domain.infrastructure.context;

import com.lacker.micros.domain.entity.Identity;
import org.springframework.core.NamedThreadLocal;

public class SessionContext {

    private static ThreadLocal<Identity> identityHolder = new NamedThreadLocal<>("Identity");
    private static ThreadLocal<String> tenantHolder = new NamedThreadLocal<>("Tenant");

    public static Identity get() {
        return identityHolder.get();
    }

    public static void set(Identity value) {
        identityHolder.set(value);
    }

    public static void remove() {
        identityHolder.remove();
    }

    public static String getTenant() {
        return tenantHolder.get();
    }

    public static void setTenant(String value) {
        tenantHolder.set(value);
    }

    public static void removeTenant() {
        tenantHolder.remove();
    }
}
