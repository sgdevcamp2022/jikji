package com.jikji.contentcommand.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.jikji.contentcommand.domain.ImageUrl;
import com.jikji.contentcommand.dto.request.ContentCreateRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
class ContentCommandControllerTest {

    @LocalServerPort
    int port;

    @Test
    @DisplayName("content를 저장한다")
    void save() {
        // given
        Long userId = 1L;
        List<ImageUrl> imageUrl = List.of(new ImageUrl("https://mockup.url", 1, userId));
        ContentCreateRequest contentCreateRequest = ContentCreateRequest.builder()
                .userId(userId)
                .visibleComments(true)
                .visibleLikes(true)
                .imageUrl(imageUrl)
                .text("description")
                .build();

        // when
        ExtractableResponse<Response> response = saveContent("http://localhost:"+ port + "/contents", contentCreateRequest);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    static ExtractableResponse<Response> saveContent(final String api, final ContentCreateRequest request) {
        return RestAssured
                .given().log().all()
                .accept("application/json")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post(api)
                .then().log().all()
                .extract();
    }
}