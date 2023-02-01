package com.jikji.contentcommand.service;

import com.jikji.contentcommand.domain.Bookmark;
import com.jikji.contentcommand.domain.Content;
import com.jikji.contentcommand.exception.BookmarkNotFoundException;
import com.jikji.contentcommand.exception.ContentNotFoundException;
import com.jikji.contentcommand.repository.BookmarkCommandRepository;
import com.jikji.contentcommand.repository.ContentCommandRepository;
import com.jikji.contentcommand.util.ValidCheckUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookmarkCommandServiceImpl implements BookmarkCommandService {

    private final BookmarkCommandRepository bookmarkCommandRepository;

    private final ContentCommandRepository contentCommandRepository;

    @Override
    public Long saveBookmark(Long userId, Long contentId) {
        ValidCheckUtil.checkDuplicatedBookmark(userId, contentId, bookmarkCommandRepository);

        Content content = contentCommandRepository.findById(contentId)
                .orElseThrow(ContentNotFoundException::new);

        Bookmark savedBookmark = bookmarkCommandRepository
                .save(Bookmark.builder()
                        .contentId(contentId)
                        .userId(userId)
                        .build());

        return savedBookmark.getId();
    }

    @Override
    public void unsaveBookmark(Long userId, Long contentId) {
        Bookmark bookmark = bookmarkCommandRepository.findByUserIdAndContentId(userId, contentId)
                .orElseThrow(BookmarkNotFoundException::new);

        bookmarkCommandRepository.delete(bookmark);
    }
}
