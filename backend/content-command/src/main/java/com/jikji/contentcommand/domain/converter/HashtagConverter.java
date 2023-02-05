package com.jikji.contentcommand.domain.converter;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.List;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import lombok.extern.slf4j.Slf4j;

@Converter
@Slf4j
public class HashtagConverter implements AttributeConverter<List<Long>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<Long> data) {
        try {
            return objectMapper.writeValueAsString(data);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Long> convertToEntityAttribute(String data) {
        try {
            return Arrays.asList(objectMapper.readValue(data, Long[].class));
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
