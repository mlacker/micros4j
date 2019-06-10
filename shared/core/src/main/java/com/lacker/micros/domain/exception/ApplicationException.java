package com.lacker.micros.domain.exception;

/**
 * 应用异常
 */
public class ApplicationException extends RuntimeException {

    ApplicationException(String message) {
        super(message);
    }

    ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}
