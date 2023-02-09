package instagram_clone.sgdevcamp_jikji_insta_clone_notification_server.sse.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import instagram_clone.sgdevcamp_jikji_insta_clone_notification_server.sse.domain.Notification;
import instagram_clone.sgdevcamp_jikji_insta_clone_notification_server.sse.util.LocalDateTimeToArray;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NotificationResponse {

	private Long id;
	private String content;
	private String url;
	private Integer[] createdAt;
	private boolean read;

	@Builder
	public NotificationResponse(Long id, String content, String url, LocalDateTime createdAt, boolean read) {
		this.id = id;
		this.content = content;
		this.url = url;
		this.createdAt = LocalDateTimeToArray.convert(createdAt);
		this.read = read;
	}

	public static NotificationResponse from(Notification notification) {
		return NotificationResponse.builder()
			.id(notification.getId())
			.content(notification.getContent())
			.url(notification.getUrl())
			.createdAt(notification.getCreateAt())
			.read(notification.isRead())
			.build();
	}
}
