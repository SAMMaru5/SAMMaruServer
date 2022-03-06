package com.sammaru5.sammaru.exception;

public class AlreadyExistBoardnameException extends RuntimeException {
    private static final String MESSAGE = "Boardname is already taken!";

    public AlreadyExistBoardnameException() {
        super(MESSAGE);
    }
}
