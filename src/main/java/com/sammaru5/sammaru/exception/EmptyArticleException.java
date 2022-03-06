package com.sammaru5.sammaru.exception;

public class EmptyArticleException extends RuntimeException{
    private static final String MESSAGE = "Article's title or content is empty";

    public EmptyArticleException() {
        super(MESSAGE);
    }
}
