package com.sammaru5.sammaru.exception;

public class NoPermissionException extends RuntimeException{
    private static final String MESSAGE = "No permission";

    public NoPermissionException() {
        super(MESSAGE);
    }
}
