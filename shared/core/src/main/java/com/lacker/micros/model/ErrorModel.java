package com.lacker.micros.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ErrorModel {

    private String message;
    private Throwable cause;

    public ErrorModel(String message) {
        this.message = message;
    }

    public ErrorModel(String message, Throwable throwable) {
        this(message);
        this.cause = throwable;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return this.getCause().map(Throwable::getMessage).orElse(null);
    }

    public List<String> getStackTrace() {
        List<String> stackTrace = new ArrayList<>();

        if (this.getCause().isPresent()) {
            StackTraceElement[] elements = this.getCause().get().getStackTrace();

            for (StackTraceElement element : elements) {
                if (element.getClassName().startsWith("com.lacker.micros")) {
                    stackTrace.add(element.toString());
                }
            }
        }

        return stackTrace;
    }

    private Optional<Throwable> getCause() {
        return Optional.ofNullable(cause);
    }
}
