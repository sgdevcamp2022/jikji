package com.jikji.contentquery.service;


import com.jikji.contentquery.domain.Content;
import com.jikji.contentquery.repository.ContentQueryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContentQueryServiceImpl implements ContentQueryService {

    private final ContentQueryRepository contentQueryRepository;

    public Content findByPostId(Long contentId) {
        return contentQueryRepository.findByContentId(contentId).orElseThrow(
                () -> new RuntimeException("해당 게시글이 존재하지 않습니다.")
        );
    }

    public List<Content> findByUserId(Long userId) {
        return contentQueryRepository.findByUserId(userId);
    }
}
