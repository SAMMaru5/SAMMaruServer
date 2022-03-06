package com.sammaru5.sammaru.exception;

public class NonExistentAritcleException extends RuntimeException{
    private static final String MESSAGE = "Article doesn't exist";

    public NonExistentAritcleException() {
        super(MESSAGE);
    }
}
