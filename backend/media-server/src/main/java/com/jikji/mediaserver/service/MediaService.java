package com.jikji.mediaserver.service;

import java.io.IOException;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.jikji.mediaserver.dto.MediaDto;
import com.jikji.mediaserver.dto.MediaResponseData;
import com.jikji.mediaserver.exception.CustomException;
import com.jikji.mediaserver.exception.ErrorCode;
import com.jikji.mediaserver.model.Media;
import com.jikji.mediaserver.model.User;
import com.jikji.mediaserver.repository.MediaRepository;
import com.jikji.mediaserver.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MediaService {
	private final MediaRepository mediaRepository;
	private final S3Service s3Service;
	private final UserRepository userRepository;

	@Transactional
	public MediaResponseData save(MediaDto mediaDto) throws IOException {
		User tempUser = userRepository.findById(mediaDto.getUserId())
			.orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
		s3Service.uploadMediaToS3(mediaDto, tempUser.getUserName());

		Media media = Media.builder().url(mediaDto.getUrl())
			.mediaType(mediaDto.getMediaType())
			.user(tempUser).build();
		mediaRepository.save(media);

		MediaResponseData mediaResponseData = MediaResponseData.builder()
			.mediaType(media.getMediaType())
			.url(media.getUrl())
			.build();
		return mediaResponseData;
	}
}
