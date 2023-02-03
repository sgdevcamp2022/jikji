package com.jikji.contentcommand.exception;

public class BookmarkNotFoundException extends RuntimeException {

    private final static String MSG = "BOOKMARK_NOT_FOUND";

    public BookmarkNotFoundException() {
        super(MSG);
    }
}
