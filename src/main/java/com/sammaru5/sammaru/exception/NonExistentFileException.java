package com.sammaru5.sammaru.exception;

public class NonExistentFileException extends RuntimeException {
    private static final String MESSAGE = "File doesn't exist";

    public NonExistentFileException() {
        super(MESSAGE);
    }
}
