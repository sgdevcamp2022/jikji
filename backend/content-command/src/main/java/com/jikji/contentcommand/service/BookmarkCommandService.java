package com.jikji.contentcommand.service;

public interface BookmarkCommandService {

    Long saveBookmark(Long userId, Long contentId);

    void unsaveBookmark(Long userId, Long contentId);
}
