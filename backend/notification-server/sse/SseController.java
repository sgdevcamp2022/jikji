package instagram_clone.sgdevcamp_jikji_insta_clone_auth_server.sse;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import instagram_clone.sgdevcamp_jikji_insta_clone_auth_server.jwt.service.JwtService;
import instagram_clone.sgdevcamp_jikji_insta_clone_auth_server.user.User;
import instagram_clone.sgdevcamp_jikji_insta_clone_auth_server.user.service.UserService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class SseController {

	public static Map<String, SseEmitter> sseEmitters = new ConcurrentHashMap<>();
	private final JwtService jwtService;
	private final NotificationService notificationService;
	private final UserService userService;

	public SseController(JwtService jwtService, NotificationService notificationService, UserService userService) {
		this.jwtService = jwtService;
		this.notificationService = notificationService;
		this.userService = userService;
	}

	@CrossOrigin
	@GetMapping(value = "/sub", consumes = MediaType.ALL_VALUE)
	public SseEmitter subscribe(@RequestParam String token,
		@RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "")
		String lastEventId) {
		String email = jwtService.getEmail(token);
		SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
		try {
			emitter.send(SseEmitter.event().name("connect"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		sseEmitters.put(email, emitter);
		System.out.println("sseEmitters = " + sseEmitters);
		emitter.onCompletion(() -> sseEmitters.remove(email));
		emitter.onTimeout(() -> sseEmitters.remove(email));
		emitter.onError((e) -> sseEmitters.remove(email));

		return emitter;
	}

	@PostMapping("/add")
	public ResponseEntity<?> add() {
		List<User> all = userService.findAll();
		for (User user : all) {
			notificationService.noticeAllMember(user.getEmail());
		}
		return ResponseEntity.ok("ok");
	}

}
