package com.jikji.mediaserver.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jikji.mediaserver.dto.*;
import com.jikji.mediaserver.service.MediaService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MediaController {
	private final MediaService mediaService;

	@PostMapping(value = "/media", consumes = {"multipart/form-data"})
	public ResponseEntity<ResultResponse> uploadMedia(@ModelAttribute MediaDto mediaDto) throws IOException {
		log.info(mediaDto.getFile().getOriginalFilename());
		MediaResponseData mediaResponseData = mediaService.save(mediaDto);
		return ResponseEntity.ok(new ResultResponse(ResultCode.MEDIA_UPLOAD_SUCCESS, mediaResponseData));
	}
}
