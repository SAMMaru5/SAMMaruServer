package com.sammaru5.sammaru.exception;

public class AlreadyExistStudentIdException extends RuntimeException {
    private static final String MESSAGE = "Student Id is already taken!";
    public AlreadyExistStudentIdException() {
        super(MESSAGE);
    }
}
