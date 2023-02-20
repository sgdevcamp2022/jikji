package instagram_clone.sgdevcamp_jikji_insta_clone_notification_server;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import instagram_clone.sgdevcamp_jikji_insta_clone_notification_server.chat.ChatMessage;
import instagram_clone.sgdevcamp_jikji_insta_clone_notification_server.kafka.dto.UserInfo;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {
	@Value("${spring.kafka.bootstrap-servers}")
	private String servers;

	@Bean
	public ConsumerFactory<String, ChatMessage> consumerFactory() {
		Map<String, Object> config = new HashMap<>();

		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
		config.put(ConsumerConfig.GROUP_ID_CONFIG, "jikji");

		return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(),
			new JsonDeserializer<>(ChatMessage.class, false));
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, ChatMessage> kafkaListener() {
		ConcurrentKafkaListenerContainerFactory<String, ChatMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		return factory;
	}

	@Bean
	public ConsumerFactory<String, UserInfo> consumerUserFactory() {
		Map<String, Object> config = new HashMap<>();

		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
		config.put(ConsumerConfig.GROUP_ID_CONFIG, "jikji");

		return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(),
			new JsonDeserializer<>(UserInfo.class, false));
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, UserInfo> kafkaUserListener() {
		ConcurrentKafkaListenerContainerFactory<String, UserInfo> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerUserFactory());
		return factory;
	}
}
