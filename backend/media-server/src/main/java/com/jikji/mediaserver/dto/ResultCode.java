package com.jikji.mediaserver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {

	MEDIA_UPLOAD_SUCCESS(200, "M001", "미디어 업로드에 성공하였습니다");

	private final int status;
	private final String code;
	private final String message;
}

