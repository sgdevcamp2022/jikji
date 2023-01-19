package com.jikji.contentcommand.domain;

import lombok.Data;

@Data
public class ImageUrl {

    private String url;

    private int order;

    private Long userId;
}