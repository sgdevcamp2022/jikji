package com.jikji.contentquery.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jikji.contentquery.domain.Content;
import com.jikji.contentquery.dto.message.HashtagKafkaMessage;
import com.jikji.contentquery.exception.ContentNotFoundException;
import com.jikji.contentquery.repository.ContentQueryRepository;
import com.jikji.contentquery.util.KafkaTopic;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaContentConsumer {

    private final ObjectMapper mapper;

    private final ContentQueryRepository contentQueryRepository;

    @KafkaListener(topics = KafkaTopic.ADD_CONTENT)
    public void addContent(String message) throws JsonProcessingException {
        Content content = readContentByJson(message);
        contentQueryRepository.save(content);
    }

    @KafkaListener(topics = KafkaTopic.UPDATE_CONTENT)
    public void updateContent(String message) throws JsonProcessingException {
        Content content = readContentByJson(message);
        Content storedContent = getContentById(content.getContentId());
        storedContent.updateContent(content);
        contentQueryRepository.save(storedContent);
    }

    @KafkaListener(topics = KafkaTopic.DELETE_CONTENT)
    public void deleteContent(String message) throws JsonProcessingException {
        log.info("[Kafka message]: " + message);
        Long contentId = mapper.readValue(message, Long.class);
        Content storedContent = getContentById(contentId);
        contentQueryRepository.delete(storedContent);
    }

    @KafkaListener(topics = KafkaTopic.UPDATE_HASHTAG)
    public void updateHashtag(String message) throws JsonProcessingException {
        HashtagKafkaMessage hashtags = mapper.readValue(message, HashtagKafkaMessage.class);
        Long contentId = hashtags.getContentId();

        Content content = getContentById(contentId);

        content.updateHashtags(hashtags.getHashtags());
        contentQueryRepository.save(content);
    }

    @KafkaListener(topics = KafkaTopic.VISIBLE_LIKES)
    public void visibilityLikes(String message) throws JsonProcessingException {
        Long contentId = mapper.readValue(message, Long.class);
        Content content = getContentById(contentId);
        content.updateVisibilityLikes();
        contentQueryRepository.save(content);
    }

    @KafkaListener(topics = KafkaTopic.VISIBLE_COMMENT)
    public void visibilityComments(String message) throws JsonProcessingException {
        Long contentId = mapper.readValue(message, Long.class);
        Content content = getContentById(contentId);
        content.updateVisibilityComments();
        contentQueryRepository.save(content);
    }

    private Content getContentById(Long id) {
        return contentQueryRepository.findByContentId(id)
                .orElseThrow(ContentNotFoundException::new);
    }

    private Content readContentByJson(String json) throws JsonProcessingException {
        log.info("[Kafka message]: " + json);
        return mapper.readValue(json, Content.class);
    }
}
