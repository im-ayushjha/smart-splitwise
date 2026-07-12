package com.ayush.smart_splitwise.common.exception.custom;

public class PasswordMismatchException extends RuntimeException {
    public PasswordMismatchException(String message) {
        super(message);
    }
}
