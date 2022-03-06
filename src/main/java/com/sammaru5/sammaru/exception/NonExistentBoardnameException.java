package com.sammaru5.sammaru.exception;

public class NonExistentBoardnameException extends RuntimeException{
    private static final String MESSAGE = "Boardname doesn't exist";

    public NonExistentBoardnameException() {
        super(MESSAGE);
    }
}
