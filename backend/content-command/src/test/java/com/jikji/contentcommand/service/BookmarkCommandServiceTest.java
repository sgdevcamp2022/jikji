package com.jikji.contentcommand.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.jikji.contentcommand.domain.Bookmark;
import com.jikji.contentcommand.domain.Content;
import com.jikji.contentcommand.domain.ImageUrl;
import com.jikji.contentcommand.exception.BookmarkDuplicatedException;
import com.jikji.contentcommand.repository.BookmarkCommandRepository;
import com.jikji.contentcommand.repository.ContentCommandRepository;
import com.jikji.contentcommand.util.ValidCheckUtil;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
class BookmarkCommandServiceTest {

    @Autowired
    private BookmarkCommandService bookmarkService;

    @Autowired
    private BookmarkCommandRepository bookmarkRepository;

    @Autowired
    private ContentCommandRepository contentRepository;

    @BeforeEach
    void setUp() {
        Content content = Content.builder()
                .id(1L)
                .likes(0)
                .text("변경전 텍스트")
                .imageUrl(List.of(new ImageUrl("http://test.jikji/before-image", 1, 1L)))
                .userId(1L)
                .visibleComments(false)
                .visibleLikes(true)
                .build();

        contentRepository.save(content);
    }

    @DisplayName("북마크를 생성한다")
    @Test
    void 북마크를_생성한다() {
        // given
        final Long userId = 1L;
        final Long contentId = 1L;

        // when
        Long savedBookmarkId = bookmarkService.saveBookmark(userId, contentId);

        // then
        assertThat(savedBookmarkId).isNotNull();
    }

    @DisplayName("북마크는 게시글당 1번만 가능하다")
    @Test
    void 북마크는_게시글당_1번만_가능하다() {
        // given
        final Long userId = 1L;
        final Long contentId = 1L;
        Bookmark bookmark = Bookmark.builder()
                .id(1L)
                .userId(userId)
                .contentId(contentId)
                .build();
        bookmarkRepository.save(bookmark);

        // when, then
        assertThrows(BookmarkDuplicatedException.class,
                () -> ValidCheckUtil.checkDuplicatedBookmark(userId, contentId, bookmarkRepository));
    }

    @DisplayName("북마크를 삭제한다")
    @Test
    void 북마크를_삭제한다() {
        // given
        final Long userId = 1L;
        final Long contentId = 1L;
        Bookmark bookmark = Bookmark.builder()
                .id(1L)
                .userId(userId)
                .contentId(contentId)
                .build();
        bookmarkRepository.save(bookmark);

        // when, then
        assertDoesNotThrow(() -> bookmarkService.unsaveBookmark(userId, contentId));
    }
}