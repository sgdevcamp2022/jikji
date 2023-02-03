package com.jikji.contentquery.exception;

public class ContentNotFoundException extends RuntimeException {

    private static final String MESSAGE = "NOT_FOUND_CONTENT";

    public ContentNotFoundException() {
        super(MESSAGE);
    }
}
