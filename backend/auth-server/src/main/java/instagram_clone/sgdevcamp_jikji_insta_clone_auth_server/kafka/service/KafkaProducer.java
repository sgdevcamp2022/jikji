package instagram_clone.sgdevcamp_jikji_insta_clone_auth_server.kafka.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import instagram_clone.sgdevcamp_jikji_insta_clone_auth_server.kafka.dto.UserInfo;

@Service
public class KafkaProducer {

	@Value(value = "jikji-user-info")
	private String topicName;

	private final KafkaTemplate<String, UserInfo> kafkaTemplate;

	public KafkaProducer(KafkaTemplate<String, UserInfo> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	public void sendMessage(UserInfo userInfo) {
		kafkaTemplate.send(topicName, userInfo);
	}
}
