package com.jikji.contentquery.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jikji.contentquery.domain.ContentLike;
import com.jikji.contentquery.repository.ContentLikeQueryRepository;
import com.jikji.contentquery.util.KafkaTopic;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaContentLikeConsumer {

    private final ObjectMapper mapper;

    private final ContentLikeQueryRepository contentLikeQueryRepository;

    @KafkaListener(topics = KafkaTopic.ADD_CONTENT_LIKE)
    public void likeContent(String message) throws JsonProcessingException {
        ContentLike contentLike = mapper.readValue(message, ContentLike.class);
        contentLikeQueryRepository.save(contentLike);
    }

    @KafkaListener(topics = KafkaTopic.DELETE_CONTENT_LIKE)
    public void unlikeContent(String message) throws JsonProcessingException {
        ContentLike contentLike = mapper.readValue(message, ContentLike.class);
        contentLikeQueryRepository.delete(contentLike);
    }
}
