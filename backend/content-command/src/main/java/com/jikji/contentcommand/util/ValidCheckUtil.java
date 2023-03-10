package com.jikji.contentcommand.util;

import com.jikji.contentcommand.exception.BookmarkDuplicatedException;
import com.jikji.contentcommand.exception.LikeDuplicatedException;
import com.jikji.contentcommand.repository.BookmarkCommandRepository;
import com.jikji.contentcommand.repository.LikeCommandRepository;

public final class ValidCheckUtil {

    public static void checkDuplicatedLike(Long userId, Long contentId,
                                           LikeCommandRepository likeCommandRepository) {
        if (likeCommandRepository.existsByUserIdAndContentId(userId, contentId)) {
            throw new LikeDuplicatedException();
        }
    }

    public static void checkDuplicatedBookmark(Long userId, Long contentId,
                                               BookmarkCommandRepository bookmarkCommandRepository) {
        if (bookmarkCommandRepository.existsByUserIdAndContentId(userId, contentId)) {
            throw new BookmarkDuplicatedException();
        }
    }
}
