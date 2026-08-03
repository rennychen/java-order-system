package com.github.renny.order.system.common.exception;

public class BaseException extends RuntimeException {
    private final int httpStatus;

    public BaseException(int httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public int getHttpStatus(){return httpStatus;}
}
