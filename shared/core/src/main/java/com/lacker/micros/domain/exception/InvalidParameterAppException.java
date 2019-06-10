package com.lacker.micros.domain.exception;

/**
 * 无效的参数异常
 */
public class InvalidParameterAppException extends ApplicationException {

    public InvalidParameterAppException() {
        super("无效的参数");
    }

    public InvalidParameterAppException(String message) {
        super(message);
    }

    public InvalidParameterAppException(String message, Throwable cause) {
        super(message, cause);
    }
}
