package com.jikji.mediaserver.dto.response;

import com.jikji.mediaserver.model.MediaType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MediaResponseData {
	private String url;
	private MediaType mediaType;
}
