package instagram_clone.sgdevcamp_jikji_insta_clone_notification_server.sse;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.springframework.util.MultiValueMap;

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

import instagram_clone.sgdevcamp_jikji_insta_clone_notification_server.sse.domain.Notification;
import instagram_clone.sgdevcamp_jikji_insta_clone_notification_server.sse.dto.NotificationDto;
import instagram_clone.sgdevcamp_jikji_insta_clone_notification_server.sse.dto.NotificationResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/notification")
public class SseController {

	private final NotificationService notificationService;

	public SseController(NotificationService notificationService) {
		this.notificationService = notificationService;
	}

	@CrossOrigin("*")
	@GetMapping(value = "/subscribe", consumes = MediaType.ALL_VALUE)
	public SseEmitter subscribe(@RequestParam String token,
		@RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "")
		String lastEventId) {

		return notificationService.subscribe(token, lastEventId);
	}

	@PostMapping("/send")
	public ResponseEntity<?> chatNotification(@RequestBody MultiValueMap<String, String> body) {
		String sender = body.get("sender").get(0);
		String receiver = body.get("receiver").get(0);
		String content = body.get("content").get(0);
		return ResponseEntity.ok("ok");
	}

	@PostMapping("/add")
	public ResponseEntity<?> send(@RequestBody NotificationDto notificationDto) {

		notificationService.save(notificationDto.toEntity());

		return ResponseEntity.ok("ok");
	}

	@GetMapping("")
	public List<NotificationResponse> findByUserId(@RequestParam Long userId){
		return notificationService.findByUserId(userId).stream()
			.map(NotificationResponse::from)
			.collect(Collectors.toList());
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

}
