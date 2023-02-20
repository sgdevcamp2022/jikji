package instagram_clone.sgdevcamp_jikji_insta_clone_notification_server.kafka.service;

import java.io.IOException;
import java.util.Objects;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import instagram_clone.sgdevcamp_jikji_insta_clone_notification_server.chat.ChatMessage;
import instagram_clone.sgdevcamp_jikji_insta_clone_notification_server.kafka.dto.UserEmail;
import instagram_clone.sgdevcamp_jikji_insta_clone_notification_server.kafka.dto.UserInfo;

@Service
public class KafkaConsumer {

	KafkaProducer kafkaProducer;

	public KafkaConsumer(KafkaProducer kafkaProducer) {
		this.kafkaProducer = kafkaProducer;
	}

	@KafkaListener(topics = "${spring.kafka.template.default-topic}", groupId = "jikji-project", containerFactory = "kafkaListener")
	public void consume(ChatMessage chatMessage) throws IOException {
		System.out.println("message = " + chatMessage);
		if (Objects.equals(chatMessage.getType(), "Chat")) {
			System.out.println("chatMessage = " + chatMessage.getSender());
			String sender = chatMessage.getSender();
			String receiver = chatMessage.getReceiver();
			UserEmail senderEmail = new UserEmail();
			UserEmail receiverEmail = new UserEmail();
			senderEmail.setEmail(sender);
			receiverEmail.setEmail(receiver);
			kafkaProducer.sendMessage(senderEmail);
			kafkaProducer.sendMessage(receiverEmail);
		}
	}

	@KafkaListener(topics = "jikji-user-info", groupId = "jikji-project", containerFactory = "kafkaUserListener")
	public void userConsume(UserInfo userInfo) throws IOException {
		System.out.println("userInfo = " + userInfo.getUserId());
		System.out.println("userInfo.getNickname() = " + userInfo.getNickname());
	}
}
