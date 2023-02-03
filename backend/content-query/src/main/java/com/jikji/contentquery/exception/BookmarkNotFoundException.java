package com.jikji.contentquery.exception;

public class BookmarkNotFoundException extends RuntimeException {

    private static final String MESSAGE = "NOT_FOUND_BOOKMARK";

    public BookmarkNotFoundException() {
        super(MESSAGE);
    }
}
