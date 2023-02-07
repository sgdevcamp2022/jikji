package instagram_clone.sgdevcamp_jikji_insta_clone_auth_server.sse;

import static instagram_clone.sgdevcamp_jikji_insta_clone_auth_server.sse.SseController.*;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
public class NotificationService {

	public void noticeAllMember(String email){
		if(sseEmitters.containsKey(email)){
			SseEmitter emitter = sseEmitters.get(email);
			try{
				System.out.println("emitter = " + emitter);
				emitter.send(SseEmitter.event().name("add").data("알림 테스트입니다!"));
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
}
