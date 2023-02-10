package com.jikji.mediaserver.util;

import com.jikji.mediaserver.exception.CustomException;
import com.jikji.mediaserver.exception.ErrorCode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MediaUtil {
	public static String findContentType(String contentType) {
		String[] mediaContentType = contentType.split("/");
		if (mediaContentType.length <= 0)
			throw new CustomException(ErrorCode.FILE_CONVERT_FAIL);
		if (!(mediaContentType[0].toUpperCase().equals("IMAGE") || mediaContentType[0].toUpperCase().equals("VIDEO")))
			throw new CustomException(ErrorCode.FILE_CONVERT_FAIL);
		return mediaContentType[0].toUpperCase();
	}

	public static String findFolder(String filename, String userName, String contentType) {
		String folder = "";
		folder += contentType + "/" + userName + "/" + contentType;
		return folder;
	}

	public static String findExt(String fileName) {
		return fileName.split("\\.")[1];
	}
}
