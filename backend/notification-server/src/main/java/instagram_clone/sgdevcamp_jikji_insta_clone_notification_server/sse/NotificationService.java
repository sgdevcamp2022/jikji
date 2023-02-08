package instagram_clone.sgdevcamp_jikji_insta_clone_notification_server.sse;

import static instagram_clone.sgdevcamp_jikji_insta_clone_notification_server.sse.SseController.*;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import instagram_clone.sgdevcamp_jikji_insta_clone_notification_server.sse.repository.EmitterRepository;

@Service
public class NotificationService {

	EmitterRepository emitterRepository;

	public NotificationService(EmitterRepository emitterRepository) {
		this.emitterRepository = emitterRepository;
	}

	public void noticeAllMember(String email) {
		if (sseEmitters.containsKey(email)) {
			SseEmitter emitter = sseEmitters.get(email);
			try {
				System.out.println("emitter = " + emitter);
				emitter.send(SseEmitter.event().name("add").data("알림 테스트입니다!"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void save(String id, SseEmitter emitter) {
		emitterRepository.save(id, emitter);
	}

	public Map<String, SseEmitter> findAllWithId(String id) {
		return emitterRepository.findAllWithId(id);
	}
}
