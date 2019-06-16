package com.lacker.micros.domain.infrastructure.uuid;

import java.util.UUID;

public class UUIDGenerator {

    private static final UUIDGenerator INSTANCE = new UUIDGenerator();

    private static short counter = (short) 0;

    public static String sequentialUUIDString() {
        return INSTANCE.generateUUIDString();
    }

    private String generateUUIDString() {
        return generateUUID().toString().toUpperCase();
    }

    private UUID generateUUID() {
        long mostSignificantBits = (System.currentTimeMillis() << 20) | (getCount());
        long leastSignificantBits = UUID.randomUUID().getLeastSignificantBits();

        return new UUID(mostSignificantBits, leastSignificantBits);
    }

    private short getCount() {
        synchronized (UUIDGenerator.class) {
            if (counter < 0) {
                counter = 0;
            }
            return counter++;
        }
    }
}