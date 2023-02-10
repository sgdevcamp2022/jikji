package instagram_clone.sgdevcamp_jikji_insta_clone_notification_server.sse.dto;
import java.time.LocalDateTime;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import instagram_clone.sgdevcamp_jikji_insta_clone_notification_server.sse.domain.Notification;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NotificationDto {

	@Id
	@GeneratedValue
	private Long id;
	private Long userId;
	private String content;
	private String url;

	@Builder
	public NotificationDto(Long id, Long userId, String content, String url) {
		this.id = id;
		this.userId = userId;
		this.content = content;
		this.url = url;
	}

	public Notification toEntity() {
		return Notification.builder()
			.id(id)
			.userId(userId)
			.content(content)
			.url(url)
			.createdAt(LocalDateTime.now())
			.isRead(false)
			.build();
	}

}
