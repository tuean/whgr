package com.tuean.whgr.exception;

public class NeedLoginException extends RuntimeException {

    public NeedLoginException() {
    }

    public NeedLoginException(String message) {
        super(message);
    }

    public NeedLoginException(String message, Throwable cause) {
        super(message, cause);
    }

    public NeedLoginException(Throwable cause) {
        super(cause);
    }

    public NeedLoginException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
