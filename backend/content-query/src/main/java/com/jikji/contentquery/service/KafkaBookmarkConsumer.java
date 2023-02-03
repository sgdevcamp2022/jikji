package com.jikji.contentquery.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jikji.contentquery.domain.Bookmark;
import com.jikji.contentquery.dto.message.BookmarkKafkaMessage;
import com.jikji.contentquery.exception.BookmarkNotFoundException;
import com.jikji.contentquery.repository.BookmarkQueryRepository;
import com.jikji.contentquery.util.KafkaTopic;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaBookmarkConsumer {

    private final BookmarkQueryRepository bookmarkQueryRepository;

    private final ObjectMapper mapper;

    @KafkaListener(topics = KafkaTopic.ADD_BOOKMARK)
    public void addBookmark(String message) throws JsonProcessingException {
        BookmarkKafkaMessage bookmark = mapper.readValue(message, BookmarkKafkaMessage.class);
        bookmarkQueryRepository.save(bookmark.toEntity());
    }

    @KafkaListener(topics = KafkaTopic.DELETE_BOOKMARK)
    public void deleteBookmark(String message) throws JsonProcessingException {
        Long bookmarkId = mapper.readValue(message, Long.class);
        Bookmark bookmark = bookmarkQueryRepository.findByBookmarkId(bookmarkId).orElseThrow(
                BookmarkNotFoundException::new
        );
        bookmarkQueryRepository.delete(bookmark);
    }
}
