package com.jikji.contentcommand.dto.message;

import com.jikji.contentcommand.domain.Content;
import com.jikji.contentcommand.domain.ImageUrl;
import java.util.List;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContentKafkaMessage {

    private Long contentId;

    private Long userId;

    private String text;

    private List<ImageUrl> imageUrl;

    private List<Long> hashtags;

    private int likes;

    private Boolean visibleLikes;

    private Boolean visibleComments;

    private String createdAt;

    private String modifiedAt;

    public ContentKafkaMessage(Content content) {
        this.contentId = content.getId();
        this.userId = content.getUserId();
        this.text = content.getText();
        this.hashtags = content.getHashtags();
        this.imageUrl = content.getImageUrl();
        this.createdAt = content.getCreatedAt();
        this.modifiedAt = content.getModifiedAt();
        this.likes = content.getLikes();
        this.visibleComments = content.getVisibleComments();
        this.visibleLikes = content.getVisibleLikes();
    }
}