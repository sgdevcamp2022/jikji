package com.jikji.contentcommand.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.jikji.contentcommand.domain.Bookmark;
import com.jikji.contentcommand.domain.Content;
import com.jikji.contentcommand.dto.message.BookmarkKafkaMessage;
import com.jikji.contentcommand.exception.BookmarkNotFoundException;
import com.jikji.contentcommand.exception.ContentNotFoundException;
import com.jikji.contentcommand.repository.BookmarkCommandRepository;
import com.jikji.contentcommand.repository.ContentCommandRepository;
import com.jikji.contentcommand.util.KafkaTopic;
import com.jikji.contentcommand.util.ValidCheckUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookmarkCommandServiceImpl implements BookmarkCommandService {

    private final BookmarkCommandRepository bookmarkCommandRepository;

    private final ContentCommandRepository contentCommandRepository;

    private final KafkaTemplate<String, String> kafkaTemplate;

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

        sendMessage(savedBookmark, 0, content.getLikes());
        return savedBookmark.getId();
    }

    @Override
    public void unsaveBookmark(Long userId, Long contentId) {
        Bookmark bookmark = bookmarkCommandRepository.findByUserIdAndContentId(userId, contentId)
                .orElseThrow(BookmarkNotFoundException::new);

        bookmarkCommandRepository.delete(bookmark);
        kafkaTemplate.send(KafkaTopic.DELETE_BOOKMARK, String.valueOf(bookmark.getId()));
    }

    private void sendMessage(Bookmark bookmark, int commentCount, int likesCount) {
        ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String data = null;
        try {
            data = writer.writeValueAsString(BookmarkKafkaMessage.builder()
                    .bookmarkId(bookmark.getId())
                    .userId(bookmark.getUserId())
                    .contentId(bookmark.getContentId())
                    .commentCount(commentCount)
                    .likesCount(likesCount));

            kafkaTemplate.send(KafkaTopic.ADD_BOOKMARK, data);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
        log.info("content kafka send - " + data);
    }
}
