package com.jikji.mediaserver.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
	// Global
	INTERNAL_SERVER_ERROR(500, "M001", "내부 서버 오류입니다."),
	METHOD_NOT_ALLOWED(405, "M002", "허용되지 않은 HTTP method입니다."),
	INPUT_VALUE_INVALID(400, "M003", "유효하지 않은 입력입니다."),
	INPUT_TYPE_INVALID(400, "M004", "입력 타입이 유효하지 않습니다."),
	HTTP_MESSAGE_NOT_READABLE(400, "M005", "request message body가 없거나, 값 타입이 올바르지 않습니다."),
	HTTP_HEADER_INVALID(400, "M006", "request header가 유효하지 않습니다."),
	IMAGE_TYPE_NOT_SUPPORTED(400, "M007", "지원하지 않는 이미지 타입입니다."),
	FILE_CONVERT_FAIL(500, "M008", "변환할 수 없는 파일입니다."),
	USER_NOT_FOUND(404, "M009", "유저가 존재하지 않습니다.");
	private final int status;
	private final String code;
	private final String message;
}
