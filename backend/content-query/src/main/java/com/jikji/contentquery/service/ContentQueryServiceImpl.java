package com.jikji.contentquery.service;


import com.jikji.contentquery.domain.Content;
import com.jikji.contentquery.exception.ContentNotFoundException;
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

    public Content findByContentId(Long contentId) {
        return contentQueryRepository.findByContentId(contentId)
                .orElseThrow(ContentNotFoundException::new);
    }

    public List<Content> findByUserId(Long userId) {
        return contentQueryRepository.findByUserId(userId);
    }
}
