package com.jikji.contentquery.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ImageUrl {

    private String url;

    private int order;

    private int height;

    private int width;

    @JsonProperty(value = "user_id")
    private Long userId;
}
