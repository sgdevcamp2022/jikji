package instagram_clone.sgdevcamp_jikji_insta_clone_notification_server.sse;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import instagram_clone.sgdevcamp_jikji_insta_clone_notification_server.sse.domain.Notification;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/notification")
public class SseController {

	public static Map<String, SseEmitter> sseEmitters = new ConcurrentHashMap<>();

	private final NotificationService notificationService;

	public SseController(NotificationService notificationService) {
		this.notificationService = notificationService;
	}

	@CrossOrigin("*")
	@GetMapping(value = "/subscribe", consumes = MediaType.ALL_VALUE)
	public SseEmitter subscribe(@RequestParam String token,
		@RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "")
		String lastEventId) {
		String id = token + "_" + System.currentTimeMillis();
		System.out.println("email = " + token);
		SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
		try {
			emitter.send(SseEmitter.event().name("connect"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		notificationService.save(id,emitter);
		notificationService.saveDb(new Notification(id));

		emitter.onCompletion(() -> sseEmitters.remove(token));
		emitter.onTimeout(() -> sseEmitters.remove(token));
		emitter.onError((e) -> sseEmitters.remove(token));

		return emitter;
	}


	@PostMapping("/add")
	public ResponseEntity<?> add() {


		return ResponseEntity.ok("ok");
	}

}
