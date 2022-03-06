package com.sammaru5.sammaru.exception;

public class InvalidUserException extends RuntimeException {
    private static final String MESSAGE = "user is not valid!";
    public InvalidUserException() {
        super(MESSAGE);
    }
}