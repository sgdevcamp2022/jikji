package instagram_clone.sgdevcamp_jikji_insta_clone_notification_server.kafka.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import instagram_clone.sgdevcamp_jikji_insta_clone_notification_server.kafka.dto.UserEmail;

@Service
public class KafkaProducer {

	@Value(value = "jikji-user-email")
	private String topicName;

	private final KafkaTemplate<String, UserEmail> kafkaTemplate;

	public KafkaProducer(KafkaTemplate<String, UserEmail> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	public void sendMessage(UserEmail email) {
		System.out.println("email = " + email);
		kafkaTemplate.send(topicName, email);
	}
}
