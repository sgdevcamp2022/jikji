package com.jikji.contentcommand.exception;

public class ContentNotFoundException extends RuntimeException {

    private static final String MESSAGE = "CONTENT_NOT_FOUND";

    public ContentNotFoundException() {
        super(MESSAGE);
    }
}
