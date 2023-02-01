package com.jikji.contentcommand.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.jikji.contentcommand.domain.Content;
import com.jikji.contentcommand.domain.ImageUrl;
import com.jikji.contentcommand.dto.request.ContentCreateRequest;
import com.jikji.contentcommand.dto.request.ContentUpdateRequest;
import com.jikji.contentcommand.repository.ContentCommandRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ContentCommandServiceTest {

    @Autowired
    private ContentCommandService contentCommandService;
    @Autowired
    private ContentCommandRepository contentCommandRepository;

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

    @DisplayName("게시글을 삭제한다")
    @Test
    void 게시글을_삭제한다() {
        // given
        Long contentId = 1L;
        Content content = Content.builder()
                .id(contentId)
                .likes(0)
                .text("게시글 본문")
                .imageUrl(List.of(new ImageUrl("http://test.jikji/image", 1, 1L)))
                .userId(1L)
                .visibleComments(false)
                .visibleLikes(false)
                .build();
        contentCommandRepository.save(content);
        // when, then
        assertDoesNotThrow(() -> contentCommandService.delete(contentId));
    }

    @DisplayName("댓글 감추기 요청시 댓글을 비활성화 한다")
    @Test
    void 댓글을_비활성화한다() {
        // given
        Long contentId = 1L;
        boolean visibleComments = true;
        Content content = Content.builder()
                .id(contentId)
                .likes(0)
                .text("게시글 본문")
                .imageUrl(List.of(new ImageUrl("http://test.jikji/image", 1, 1L)))
                .userId(1L)
                .visibleComments(visibleComments)
                .build();
        contentCommandRepository.save(content);

        // when
        boolean result = contentCommandService.visibilityComments(contentId);

        // then
        assertThat(result).isEqualTo(!visibleComments);
    }

    @DisplayName("좋아요 숫자 감추기 요청시 좋아요 수를 가린다")
    @Test
    void 좋아요수를_가린다() {
        // given
        Long contentId = 1L;
        boolean visibleLikes = true;
        Content content = Content.builder()
                .id(contentId)
                .likes(0)
                .text("게시글 본문")
                .imageUrl(List.of(new ImageUrl("http://test.jikji/image", 1, 1L)))
                .userId(1L)
                .visibleLikes(visibleLikes)
                .build();
        contentCommandRepository.save(content);

        // when
        boolean result = contentCommandService.visibilityLikes(contentId);

        // then
        assertThat(result).isEqualTo(!visibleLikes);
    }
}