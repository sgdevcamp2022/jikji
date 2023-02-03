package com.jikji.contentcommand.exception;

public class BookmarkDuplicatedException extends RuntimeException {

    private final static String MSG = "BOOKMARK_DUPLICATED";

    public BookmarkDuplicatedException() {
        super(MSG);
    }
}
