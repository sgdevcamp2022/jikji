package com.jikji.contentcommand.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.jikji.contentcommand.domain.Content;
import com.jikji.contentcommand.domain.ImageUrl;
import com.jikji.contentcommand.dto.request.ContentCreateRequest;
import com.jikji.contentcommand.dto.request.ContentUpdateRequest;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ContentCommandServiceTest {

    @Autowired
    private ContentCommandService contentCommandService;

    @DisplayName("새로운 게시글을 생성한다")
    @Test
    void 새로운_게시글을_생성한다() {
        // given
        Long userId = 1L;
        String text = "본문 #해시태그 @test1234";
        boolean visibleComments = false;
        boolean visibleLikes = false;
        String url = "http://test.jikji/asd12sdf452";
        int order = 1;
        List<ImageUrl> imageUrl = List.of(new ImageUrl(url, order, userId));

        ContentCreateRequest contentCreateRequest = new ContentCreateRequest(userId, text,
                visibleComments, visibleLikes, imageUrl);

        // when
        Long savedId = contentCommandService.save(contentCreateRequest);

        // then
        assertThat(savedId).isNotNull();
    }

    @DisplayName("게시글을 수정한다")
    @Test
    void 게시글을_수정한다() {
        // given
        Content content = Content.builder()
                .id(1L)
                .likes(0)
                .text("변경전 텍스트")
                .imageUrl(List.of(new ImageUrl("http://test.jikji/before-image", 1, 1L)))
                .userId(1L)
                .visibleComments(false)
                .visibleLikes(false)
                .build();

        ContentUpdateRequest request = ContentUpdateRequest.builder()
                .text("변경된 텍스트")
                .userId(1L)
                .imageUrl(List.of(new ImageUrl("http://test.jikji/changed-image-url", 1, 1L)))
                .visibleComments(true)
                .visibleLikes(true)
                .build();
        // when
        content.update(request);

        // then
        assertThat(content.getText()).isEqualTo(request.getText());
        assertThat(content.getImageUrl()).isEqualTo(request.getImageUrl());
        assertThat(content.getVisibleComments()).isEqualTo(request.getVisibleComments());
        assertThat(content.getVisibleLikes()).isEqualTo(request.getVisibleLikes());
    }
}