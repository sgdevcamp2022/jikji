package com.jikji.contentcommand.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageUrl {

    private String url;

    private int order;

    private Long userId;

    @Builder
    public ImageUrl(String url, int order, Long userId) {
        this.url = url;
        this.order = order;
        this.userId = userId;
    }
}