package com.lacker.micros.domain.exception;

/**
 * 没有找到异常
 */
public class NotFoundAppException extends ApplicationException {

    public NotFoundAppException() {
        super("未找到对应的数据");
    }

    public NotFoundAppException(String message) {
        super(message);
    }

    public NotFoundAppException(String message, Throwable cause) {
        super(message, cause);
    }
}
