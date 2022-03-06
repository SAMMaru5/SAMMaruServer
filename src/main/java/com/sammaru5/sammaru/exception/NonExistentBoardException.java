package com.sammaru5.sammaru.exception;

public class NonExistentBoardException extends RuntimeException {
    private static final String MESSAGE = "None of boards exist";

    public NonExistentBoardException() {
        super(MESSAGE);
    }
}
