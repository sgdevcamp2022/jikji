package instagram_clone.sgdevcamp_jikji_insta_clone_notification_server.sse.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Builder
public class Notification {

	@Id
	@GeneratedValue
	private Long id;
	private Long userId;
	private String content;
	private String url;
	private LocalDateTime createdAt;
	private boolean isRead;

	@Builder
	public Notification(Long id, Long userId, String content, String url, LocalDateTime createdAt, boolean isRead) {
		this.id = id;
		this.userId = userId;
		this.content = content;
		this.url = url;
		this.createdAt = createdAt;
		this.isRead = isRead;
	}

	public void readNotification(){
		this.isRead =true;
	}
}
