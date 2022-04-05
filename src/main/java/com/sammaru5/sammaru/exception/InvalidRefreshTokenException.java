package com.sammaru5.sammaru.exception;

public class InvalidRefreshTokenException extends RuntimeException {
    private static final String MESSAGE = "리프레쉬 토큰이 유효하지 않습니다!";
    public InvalidRefreshTokenException() {
        super(MESSAGE);
    }
}