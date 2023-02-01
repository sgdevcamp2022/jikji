package com.jikji.contentcommand.service;

import com.jikji.contentcommand.domain.Content;
import com.jikji.contentcommand.dto.request.ContentCreateRequest;
import com.jikji.contentcommand.dto.request.ContentUpdateRequest;
import com.jikji.contentcommand.exception.ContentNotFoundException;
import com.jikji.contentcommand.repository.ContentCommandRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ContentCommandServiceImpl implements ContentCommandService {

    private final ContentCommandRepository contentCommandRepository;

    @Override
    public Long save(final ContentCreateRequest request) {
        Content savedContent = contentCommandRepository.save(request.toEntity());
        return savedContent.getId();
    }

    @Override
    public void update(Long contentId, ContentUpdateRequest request) {
        final Content content = contentCommandRepository.findById(contentId)
                .orElseThrow(ContentNotFoundException::new);
        content.update(request);
    }
}