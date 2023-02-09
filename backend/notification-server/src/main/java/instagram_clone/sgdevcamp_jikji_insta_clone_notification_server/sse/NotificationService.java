package instagram_clone.sgdevcamp_jikji_insta_clone_notification_server.sse;

//import static instagram_clone.sgdevcamp_jikji_insta_clone_notification_server.sse.SseController.*;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import instagram_clone.sgdevcamp_jikji_insta_clone_notification_server.sse.domain.Notification;
import instagram_clone.sgdevcamp_jikji_insta_clone_notification_server.sse.repository.EmitterRepository;
import instagram_clone.sgdevcamp_jikji_insta_clone_notification_server.sse.repository.NotificationRepository;

@Service
public class NotificationService {
	private static final Long DEFAULT_SSE_TIMEOUT = 1000 * 60 * 60 * 12L;
	EmitterRepository emitterRepository;
	NotificationRepository notificationRepository;

	public NotificationService(EmitterRepository emitterRepository, NotificationRepository notificationRepository) {
		this.emitterRepository = emitterRepository;
		this.notificationRepository = notificationRepository;
	}

	public SseEmitter subscribe(String token, String lastEventId) {
		String id = token + "_" + System.currentTimeMillis();
		SseEmitter emitter = emitterRepository.save(id, new SseEmitter(DEFAULT_SSE_TIMEOUT));

		emitter.onCompletion(() -> emitterRepository.deleteById(id));
		emitter.onTimeout(() -> emitterRepository.deleteById(id));
		emitter.onError((e) -> emitterRepository.deleteById(id));

		sendToClient(emitter, id, "Server-Sent-Event Created. [userId" + token + "]");

		if (!lastEventId.isEmpty()) {
			Map<String, SseEmitter> events = emitterRepository.findAllWithId(token);
			events.entrySet().stream()
				.filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
				.forEach(entry -> sendToClient(emitter, entry.getKey(), entry.getValue()));
		}

		return emitter;
	}

	private void sendToClient(SseEmitter emitter, String id, Object data) {
		try {
			emitter.send(SseEmitter.event()
				.id(id)
				.name("sse")
				.data(data));
		} catch (IOException e) {
			emitterRepository.deleteById(id);
			throw new RuntimeException("Connection Error");
		}
	}

	public void send(String userId, String content) {
		Map<String, SseEmitter> sseEmitters = emitterRepository.findAllWithId(userId);
		sseEmitters.forEach(
			(key, emitter) -> {
				emitterRepository.saveEventCache(userId, emitter);
				sendToClient(emitter, key, NotificationResponse.from(notification));
			}
		);
	}
	public void saveDb(Notification notification) {
		notificationRepository.save(notification);
	}


}
