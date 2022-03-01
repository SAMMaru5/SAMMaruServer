package com.sammaru5.sammaru.exception;

public class InvalidRefreshTokenException extends RuntimeException {
    private static final String MESSAGE = "refresh token is not valid!";
    public InvalidRefreshTokenException() {
        super(MESSAGE);
    }
}