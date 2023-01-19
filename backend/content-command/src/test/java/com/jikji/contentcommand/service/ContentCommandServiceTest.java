package com.jikji.contentcommand.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.jikji.contentcommand.domain.ImageUrl;
import com.jikji.contentcommand.dto.request.ContentCreateRequest;
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
}