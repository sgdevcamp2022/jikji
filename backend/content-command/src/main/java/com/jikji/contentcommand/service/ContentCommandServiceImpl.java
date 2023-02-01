package com.jikji.contentcommand.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.jikji.contentcommand.domain.Content;
import com.jikji.contentcommand.dto.message.ContentKafkaMessage;
import com.jikji.contentcommand.dto.request.ContentCreateRequest;
import com.jikji.contentcommand.dto.request.ContentUpdateRequest;
import com.jikji.contentcommand.exception.ContentNotFoundException;
import com.jikji.contentcommand.repository.ContentCommandRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.KafkaTopic;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ContentCommandServiceImpl implements ContentCommandService {

    private final ContentCommandRepository contentCommandRepository;

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public Long save(final ContentCreateRequest request) {
        Content savedContent = contentCommandRepository.save(request.toEntity());
        sendMessage(savedContent, KafkaTopic.ADD_CONTENT);
        return savedContent.getId();
    }

    @Override
    public void update(Long contentId, ContentUpdateRequest request) {
        final Content content = contentCommandRepository.findById(contentId)
                .orElseThrow(ContentNotFoundException::new);
        content.update(request);
        sendMessage(content, KafkaTopic.UPDATE_CONTENT);
    }

    @Override
    public void delete(Long contentId) {
        contentCommandRepository.deleteById(contentId);
        kafkaTemplate.send(KafkaTopic.DELETE_CONTENT, String.valueOf(contentId));
    }

    public boolean visibilityLikes(Long contentId) {
        Content content = getContent(contentId);
        content.changeVisibleLikes();
        Content savedContent = contentCommandRepository.save(content);
        kafkaTemplate.send(KafkaTopic.VISIBLE_LIKES, String.valueOf(contentId));
        return savedContent.getVisibleLikes();
    }

    public boolean visibilityComments(Long contentId) {
        Content content = getContent(contentId);
        content.changeVisibleComments();
        Content savedContent = contentCommandRepository.save(content);
        kafkaTemplate.send(KafkaTopic.VISIBLE_COMMENT, String.valueOf(contentId));
        return savedContent.getVisibleComments();
    }

    private Content getContent(Long contentId) {
        return contentCommandRepository.findById(contentId)
                .orElseThrow(ContentNotFoundException::new);
    }

    private void sendMessage(Content content, String topic) {
        ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String data = null;
        try {
            ContentKafkaMessage message = new ContentKafkaMessage(content);
            data = writer.writeValueAsString(message);
            kafkaTemplate.send(topic, data);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
        log.info("content kafka send - " + data);
    }
}