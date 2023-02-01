package com.jikji.contentquery.controller;


import com.jikji.contentquery.domain.Content;
import com.jikji.contentquery.service.ContentQueryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/contents")
public class ContentQueryController {

    private final ContentQueryService contentQueryService;

    @GetMapping("/{contentId}")
    public ResponseEntity<Content> findByPostId(@PathVariable Long contentId) {
        try {
            Content content = contentQueryService.findByPostId(contentId);
            return ResponseEntity.ok(content);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<Content>> findByUserId(@PathVariable Long userId) {
        try {
            List<Content> contents = contentQueryService.findByUserId(userId);
            return ResponseEntity.ok(contents);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
