package instagram_clone.sgdevcamp_jikji_insta_clone_auth_server.kafka.service;

import java.io.IOException;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import instagram_clone.sgdevcamp_jikji_insta_clone_auth_server.kafka.dto.UserEmail;
import instagram_clone.sgdevcamp_jikji_insta_clone_auth_server.kafka.dto.UserInfo;
import instagram_clone.sgdevcamp_jikji_insta_clone_auth_server.user.User;
import instagram_clone.sgdevcamp_jikji_insta_clone_auth_server.user.service.UserService;

@Service
public class KafkaConsumer {

	UserService userService;
	KafkaProducer kafkaProducer;

	public KafkaConsumer(UserService userService, KafkaProducer kafkaProducer) {
		this.userService = userService;
		this.kafkaProducer = kafkaProducer;
	}

	@KafkaListener(topics = "jikji-user-email", groupId = "jikji-project", containerFactory = "kafkaListener")
	public void consume(UserEmail email) throws IOException {
		System.out.println("message.getEmail() = " + email.getEmail());
		String userEmail = email.getEmail();
		User user = userService.findByEmail(userEmail);
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId(user.getId());
		userInfo.setNickname(user.getNickname());
		kafkaProducer.sendMessage(userInfo);
	}
}
