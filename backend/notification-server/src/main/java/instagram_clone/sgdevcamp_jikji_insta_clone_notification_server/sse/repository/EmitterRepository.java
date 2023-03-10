package instagram_clone.sgdevcamp_jikji_insta_clone_notification_server.sse.repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Repository
public class EmitterRepository {

	public final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();
	private final Map<String, Object> eventCache = new ConcurrentHashMap<>();

	public SseEmitter save(String id, SseEmitter emitter) {
		emitters.put(id, emitter);
		return emitter;
	}

	public void deleteById(String id) {
		emitters.remove(id);
	}

	public void deleteAllWithId(String id) {
		emitters.forEach(
			(key, emitter) -> {
				if (key.startsWith(id)) {
					emitters.remove(key);
				}
			}
		);
	}

	public Map<String, SseEmitter> findAllWithId(String id) {
		return emitters.entrySet().stream().filter(
			entry -> entry.getKey().startsWith(id)
		).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}

	public void saveEventCache(String id, Object event) {
		eventCache.put(id, event);
	}

	public Map<String, Object> findAllEventCacheWithId(String id) {
		return eventCache.entrySet().stream()
			.filter(entry -> entry.getKey().startsWith(id))
			.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}

	public void deleteAllEventCacheWithId(String id) {
		eventCache.forEach(
			(key, data) -> {
				if (key.startsWith(id)) {
					eventCache.remove(key);
				}
			}
		);
	}
}
