package com.lacker.micros.utils.id;

import java.util.concurrent.atomic.AtomicLong;

public class SequenceIdGenerator implements IdGenerator<Long> {

    private static final SequenceIdGenerator INSTANCE = new SequenceIdGenerator();
    private static final long BASE_TIMESTAMP = 1262275200000L;

    public static Long generateId() {
        return INSTANCE.generate();
    }

    private final AtomicLong sequence = new AtomicLong();
    private final long host = hashCode();

    public Long generate() {
        return (getTimestamp() << 22) | (getHost() << 12) | getSequence();
    }

    private long getTimestamp() {
        return (System.currentTimeMillis() - BASE_TIMESTAMP) & 0x01FFFFFFFFFFL;
    }

    private long getHost() {
        return host & 0x03FF;
    }

    private long getSequence() {
        return sequence.getAndIncrement() & 0x0FFF;
    }
}