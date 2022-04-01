package com.sammaru5.sammaru.exception;

public class InvalidPointException extends RuntimeException {
    private static final String MESSAGE = "Points cannot be negative!";
    public InvalidPointException() {
        super(MESSAGE);
    }
}
