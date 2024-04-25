package com.personal.gadgetstore.exception;

public class BadApiRequestException extends RuntimeException {
    public BadApiRequestException(String s) {
        super(s);
    }
}
