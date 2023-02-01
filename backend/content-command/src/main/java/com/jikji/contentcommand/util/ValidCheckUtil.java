package com.jikji.contentcommand.util;

import com.jikji.contentcommand.exception.LikeDuplicatedException;
import com.jikji.contentcommand.repository.LikeCommandRepository;

public final class ValidCheckUtil {

    public static void checkDuplicated(Long userId, Long contentId,
                                LikeCommandRepository likeCommandRepository) {
        if (likeCommandRepository.existsByUserIdAndContentId(userId, contentId)) {
            throw new LikeDuplicatedException();
        }
    }
}
