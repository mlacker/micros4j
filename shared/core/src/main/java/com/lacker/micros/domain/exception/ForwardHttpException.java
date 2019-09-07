package com.lacker.micros.domain.exception;

public class ForwardHttpException extends Exception {

    private final int status;

    public ForwardHttpException(int status, String message) {
        super(message);
        this.status = status;
    }

    public int status() {
        return this.status;
    }
}
