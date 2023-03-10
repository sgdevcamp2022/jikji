package com.jikji.contentcommand.service.comment;

import java.util.List;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.jikji.contentcommand.domain.Comment;
import com.jikji.contentcommand.domain.CommentLikes;
import com.jikji.contentcommand.dto.message.CommentKafkaMessage;
import com.jikji.contentcommand.dto.message.CommentLikeKafkaMessage;
import com.jikji.contentcommand.dto.request.CommentCreateDto;
import com.jikji.contentcommand.dto.request.CommentDto;
import com.jikji.contentcommand.dto.response.CommentResponseData;
import com.jikji.contentcommand.exception.CustomException;
import com.jikji.contentcommand.exception.ErrorCode;
import com.jikji.contentcommand.repository.CommentLikesRepository;
import com.jikji.contentcommand.repository.CommentRepository;
import com.jikji.contentcommand.util.KafkaTopic;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {

	private final CommentRepository commentRepository;
	private final CommentLikesRepository commentLikesRepository;
	private final CommentMentionService commentMentionService;
	private final KafkaTemplate<String, String> kafkaTemplate;

	@Transactional
	public CommentResponseData createComment(Long postId, CommentCreateDto commentCreateDto) {
		log.info("comment create");
		Comment comment = Comment.builder()
			.userId(commentCreateDto.getUserId())
			.userName(commentCreateDto.getUserName())
			.profileUrl(commentCreateDto.getProfileUrl())
			.description(commentCreateDto.getDescription())
			.postId(postId).build();

		commentRepository.save(comment);
		sendMessage(comment, KafkaTopic.ADD_COMMENT);

		log.info("Mention Someone For Refactoring : 멘션 알림 보내기");
		commentMentionService.mentionMember(commentCreateDto.getUserId(), commentCreateDto.getDescription());

		CommentResponseData commentResponseData = CommentResponseData.builder()
			.id(comment.getId())
			.createdAt(comment.getCreatedAt())
			.userId(comment.getUserId())
			.description(comment.getDescription())
			.build();
		return commentResponseData;
	}

	@Transactional
	public void deleteComment(Long commentId) {
		Comment comment = commentRepository.findById(commentId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_COMMENT));

		this.deleteCommentLikesAll(commentId);
		commentMentionService.deleteMentionAll(commentId);
		commentRepository.delete(comment);
		kafkaTemplate.send(KafkaTopic.DELETE_COMMENT, String.valueOf(commentId));
	}

	@Transactional
	public void updateComment(Long commentId, CommentDto commentDto) {

		Comment comment = commentRepository.findById(commentId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_COMMENT));
		comment.update(commentDto.getDescription());
	}

	@Transactional
	public void addCommentLikes(Long commentId, Long userId) {

		Comment comment = commentRepository.findById(commentId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_COMMENT));

		if (commentLikesRepository.findByUserIdAndCommentId(userId, commentId).isPresent()) {
			throw new CustomException(ErrorCode.COMMENT_ALREADY_EXIST);
		}

		comment.increaseLikes();
		commentRepository.save(comment);

		CommentLikes commentLikes = CommentLikes.builder().commentId(commentId).userId(userId).build();
		commentLikesRepository.save(commentLikes);
		sendMessage(commentLikes, KafkaTopic.ADD_COMMENT_LIKE);
		sendMessage(comment, KafkaTopic.INCREASE_COMMENT_LIKES);

	}

	@Transactional
	public void deleteCommentLikes(Long commentId, Long userId) {
		Comment comment = commentRepository.findById(commentId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_COMMENT));

		CommentLikes commentLikes = commentLikesRepository.findByUserIdAndCommentId(userId, commentId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_COMMENT));

		comment.decreaseLikes();
		commentRepository.save(comment);
		commentLikesRepository.delete(commentLikes);
		sendMessage(commentLikes, KafkaTopic.DELETE_COMMENT_LIKE);
		sendMessage(comment, KafkaTopic.DECREASE_COMMENT_LIKES);
	}

	private void deleteCommentLikesAll(Long commentId) {
		List<CommentLikes> allByComment = commentLikesRepository.findAllByCommentId(commentId);
		commentLikesRepository.deleteAll(allByComment);
	}

	private void sendMessage(Comment comment, String topic) {
		ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String data = null;
		try {
			CommentKafkaMessage message = new CommentKafkaMessage(comment);
			data = writer.writeValueAsString(message);
			kafkaTemplate.send(topic, data);
		} catch (JsonProcessingException e) {
			log.error(e.getMessage());
		}
		log.info("comment kafka send - " + data);
	}

	private void sendMessage(CommentLikes commentLikes, String topic) {
		ObjectWriter writer = new ObjectMapper().writer();
		String data = null;

		try {
			CommentLikeKafkaMessage message = CommentLikeKafkaMessage.builder()
				.commentId(commentLikes.getCommentId())
				.commentLikeId(commentLikes.getId())
				.userId(commentLikes.getUserId())
				.build();
			data = writer.writeValueAsString(message);
			kafkaTemplate.send(topic, data);
		} catch (JsonProcessingException e) {
			log.error(e.getMessage());
		}
		log.info("commentlikes kafka send - " + data);
	}
}
