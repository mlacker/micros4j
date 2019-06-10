package com.lacker.micros.domain.exception;

public class FeignException extends ApplicationException {

    private final int status;

    public FeignException(int status, String message) {
        super(message);
        this.status = status;
    }

    public int status() {
        return this.status;
    }
}
