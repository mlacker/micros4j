package com.lacker.micros.domain.exception;

/**
 * 无效的操作异常
 */
public class InvalidOperationAppException extends ApplicationException {

    public InvalidOperationAppException() {
        super("无效的操作");
    }

    public InvalidOperationAppException(String message) {
        super(message);
    }

    public InvalidOperationAppException(String message, Throwable cause) {
        super(message, cause);
    }
}
