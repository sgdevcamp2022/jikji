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

	EmitterRepository emitterRepository;
	NotificationRepository notificationRepository;

	public NotificationService(EmitterRepository emitterRepository, NotificationRepository notificationRepository) {
		this.emitterRepository = emitterRepository;
		this.notificationRepository = notificationRepository;
	}


	/* sseEmitter 인식을 못해서 오류가 나서 주석처리
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

	 */

	public void save(String id, SseEmitter emitter) {
		emitterRepository.save(id, emitter);
	}

	public void saveDb(Notification notification) {
		notificationRepository.save(notification);
	}


	public Map<String, SseEmitter> findAllWithId(String id) {
		return emitterRepository.findAllWithId(id);
	}
}
