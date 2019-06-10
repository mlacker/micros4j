package com.lacker.micros.domain.entity;

public class Option {

    private String key;
    private String text;

    public Option(String key) {
        this(key, key);
    }

    public Option(String key, String text) {
        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException("key cannot be null or empty.");
        }
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("text cannot be null or empty.");
        }

        this.key = key;
        this.text = text;
    }

    public String getKey() {
        return key;
    }

    public String getText() {
        return text;
    }
}
