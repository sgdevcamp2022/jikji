package com.example.searchservice.service;


import com.example.searchservice.dto.response.ContentResponse;
import com.example.searchservice.dto.response.HashtagResponse;
import com.example.searchservice.repository.ContentRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContentService {

    private final ContentRepository contentRepository;

    public List<ContentResponse> findByHashtag(String hashtagName, Pageable pageable) {
        return contentRepository.findAllByHashtagUsingQuery(hashtagName, pageable)
                .stream()
                .map(ContentResponse::new)
                .collect(Collectors.toList());
    }

    public HashtagResponse findHashtagInfo(String keyword) {
        Long count = contentRepository.countByHashtagsUsingQuery(keyword);
        return new HashtagResponse(keyword, count);
    }
}
