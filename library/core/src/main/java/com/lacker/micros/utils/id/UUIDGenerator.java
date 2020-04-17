package com.lacker.micros.utils.id;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class UUIDGenerator implements IdGenerator<String> {

    private static final UUIDGenerator INSTANCE = new UUIDGenerator();

    public static String generateId() {
        return INSTANCE.generate();
    }

    private final AtomicInteger counter = new AtomicInteger();

    @Override
    public String generate() {
        return generateUUID().toString().toUpperCase();
    }

    private UUID generateUUID() {
        long mostSignificantBits = (System.currentTimeMillis() << 20) | (getCount());
        long leastSignificantBits = UUID.randomUUID().getLeastSignificantBits();

        return new UUID(mostSignificantBits, leastSignificantBits);
    }

    private int getCount() {
        return counter.getAndIncrement() & 0x0FFF;
    }
}
