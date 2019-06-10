package com.lacker.micros.domain.exception;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 外套异常
 */
public class WrapperAppException extends ApplicationException {

    private final Map<String, Object> data = new LinkedHashMap<>();

    public WrapperAppException(Throwable throwable) {
        super(throwable.getMessage(), throwable);
    }

    @Override
    public StackTraceElement[] getStackTrace() {
        return super.getCause().getStackTrace();
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void addData(String key, String value) {
        data.put(key, value);
    }
}
