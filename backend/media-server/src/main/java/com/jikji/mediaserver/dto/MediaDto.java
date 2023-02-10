package com.jikji.mediaserver.dto;

import org.springframework.web.multipart.MultipartFile;

import com.jikji.mediaserver.model.MediaType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MediaDto {
	private Long userId;
	private String url;
	private MultipartFile file;
	private MediaType mediaType;
}
