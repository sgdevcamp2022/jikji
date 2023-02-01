package com.jikji.contentquery.domain;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Document(collection = "bookmarks")
@Getter
public class Bookmark {

    @Id
    @Field(value = "_id", targetType = FieldType.OBJECT_ID)
    private String id;

    private Long bookmarkId;

    private Long userId;

    private Long contentId;

    private long commentCount;

    private long likesCount;
}
