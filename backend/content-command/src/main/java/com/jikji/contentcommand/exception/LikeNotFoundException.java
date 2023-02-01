package com.jikji.contentcommand.exception;

public class LikeNotFoundException extends RuntimeException {

    private final static String MSG = "LIKE_NOT_FOUND";

    public LikeNotFoundException() {
        super(MSG);
    }
}
