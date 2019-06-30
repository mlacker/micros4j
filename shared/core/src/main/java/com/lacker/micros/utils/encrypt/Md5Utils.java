package com.lacker.micros.utils.encrypt;

import org.springframework.util.DigestUtils;

public enum Md5Utils {

    X;

    public String computeHash(String text) {
        return DigestUtils.md5DigestAsHex(text.getBytes()).toUpperCase();
    }

    public boolean verify(String text, String hash) {
        return computeHash(text).equalsIgnoreCase(hash);
    }
}
