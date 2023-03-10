package instagram_clone.sgdevcamp_jikji_insta_clone_notification_server.sse;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import instagram_clone.sgdevcamp_jikji_insta_clone_notification_server.kafka.service.KafkaConsumer;
import instagram_clone.sgdevcamp_jikji_insta_clone_notification_server.sse.domain.Notification;
import instagram_clone.sgdevcamp_jikji_insta_clone_notification_server.sse.dto.NotificationDto;
import instagram_clone.sgdevcamp_jikji_insta_clone_notification_server.sse.dto.NotificationResponse;
import instagram_clone.sgdevcamp_jikji_insta_clone_notification_server.sse.dto.SliceResponseDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class SseController {

	private final NotificationService notificationService;
	private KafkaConsumer consumer;

	public SseController(NotificationService notificationService, KafkaConsumer consumer) {
		this.notificationService = notificationService;
		this.consumer = consumer;
	}

	@CrossOrigin("*")
	@GetMapping(value = "/subscribe", consumes = MediaType.ALL_VALUE)
	public SseEmitter subscribe(@RequestParam("pk") String pk,
		@RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "")
		String lastEventId) {

		return notificationService.subscribe(pk, lastEventId);
	}

	@GetMapping("/user")
	public List<NotificationResponse> findByUserId(@RequestParam Long userId){
		return notificationService.findByUserId(userId).stream()
			.map(NotificationResponse::from)
			.collect(Collectors.toList());
	}

	@PostMapping("/add")
	public ResponseEntity<?> send(@RequestBody NotificationDto notificationDto) {

		notificationService.save(notificationDto.toEntity());

		return ResponseEntity.ok("ok");
	}

	@GetMapping("/find")
	public NotificationResponse findById(@RequestParam Long notificationId) {
		return NotificationResponse.from(notificationService.findById(notificationId));
	}

	@GetMapping("/read")
	public void readNotification(@RequestParam Long notificationId){
		notificationService.readNotification(notificationId);
	}

	@GetMapping("/delete")
	public void delete(@RequestParam Long notificationId){
		notificationService.delete(notificationService.findById(notificationId));
	}

	@GetMapping("/slicing")
	public SliceResponseDto findAllNotifications(@RequestParam Long notificationId, @RequestParam Integer size) {
		return notificationService.findAllNotifications(notificationId, size);
	}

}
