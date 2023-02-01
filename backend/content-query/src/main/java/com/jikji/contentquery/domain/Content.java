package com.jikji.contentquery.domain;

import java.util.List;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Document(collection = "contents")
@Getter
public class Content {

    @Id
    @Field(value = "_id", targetType = FieldType.OBJECT_ID)
    private String id;

    @Indexed(unique = true)
    private Long contentId;

    private Long userId;

    private String text;

    private int likes;

    private List<ImageUrl> imageUrl;

    private String createdAt;

    private String modifiedAt;

    public void updateContent(Content contentDto) {
        if (contentDto.getText() != null) {
            this.text = contentDto.getText();
        }
        if (contentDto.getImageUrl() != null) {
            this.imageUrl = contentDto.getImageUrl();
        }
    }

    public void increaseLikes() {
        this.likes++;
    }

    public void decreaseLikes() {
        this.likes--;
    }
}
