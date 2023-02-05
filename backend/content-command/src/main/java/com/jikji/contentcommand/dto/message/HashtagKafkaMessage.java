package com.jikji.contentcommand.dto.message;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HashtagKafkaMessage {

    private List<Long> hashtags;

    private Long contentId;

    @Builder
    public HashtagKafkaMessage(List<Long> hashtags, Long contentId) {
        this.hashtags = hashtags;
        this.contentId = contentId;
    }
}
