package com.jikji.contentcommand.dto.request;

import com.jikji.contentcommand.domain.Content;
import com.jikji.contentcommand.domain.ImageUrl;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor @Builder
public class ContentUpdateRequest {

    private Long userId;

    private String text;

    private List<ImageUrl> imageUrl;

    private Boolean visibleLikes;

    private Boolean visibleComments;

    private List<Long> hashtags;

    public Content toEntity() {
        return Content.builder()
                .userId(userId)
                .text(text)
                .imageUrl(imageUrl)
                .visibleLikes(visibleLikes)
                .visibleComments(visibleComments)
                .hashtags(hashtags)
                .build();
    }
}
