package com.jikji.contentquery.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("content_likes")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContentLike {

    @Id
    private String id;

    private Long contentLikeId;

    private Long userId;

    private Long contentId;

    @Builder
    public ContentLike(Long contentLikeId, Long userId, Long contentId) {
        this.contentLikeId = contentLikeId;
        this.userId = userId;
        this.contentId = contentId;
    }
}
