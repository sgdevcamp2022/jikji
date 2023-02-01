package com.jikji.contentcommand.service;

import com.jikji.contentcommand.domain.Content;
import com.jikji.contentcommand.domain.ContentLike;
import com.jikji.contentcommand.exception.ContentNotFoundException;
import com.jikji.contentcommand.exception.LikeNotFoundException;
import com.jikji.contentcommand.repository.ContentCommandRepository;
import com.jikji.contentcommand.repository.LikeCommandRepository;
import com.jikji.contentcommand.util.ValidCheckUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class LikeCommandServiceImpl implements LikeCommandService {

    private final LikeCommandRepository likeCommandRepository;

    private final ContentCommandRepository contentCommandRepository;

    @Override
    public int likeContent(Long userId, Long contentId) {
        ValidCheckUtil.checkDuplicated(userId, contentId, likeCommandRepository);

        Content content = contentCommandRepository.findById(contentId)
                .orElseThrow(ContentNotFoundException::new);
        content.increaseLikes();
        contentCommandRepository.save(content);

        ContentLike contentLike = likeCommandRepository
                .save(new ContentLike(userId, contentId));

        likeCommandRepository.save(contentLike);

        return content.getLikes();
    }

    @Override
    public int unlikeContent(Long userId, Long contentId) {
        Content content = contentCommandRepository.findById(contentId)
                .orElseThrow(ContentNotFoundException::new);
        content.decreaseLikes();
        contentCommandRepository.save(content);

        ContentLike contentLike = likeCommandRepository.findByUserIdAndContentId(userId, contentId)
                .orElseThrow(LikeNotFoundException::new);
        likeCommandRepository.delete(contentLike);

        return content.getLikes();
    }
}
