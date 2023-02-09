package instagram_clone.sgdevcamp_jikji_insta_clone_notification_server.sse.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Data;
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
	private boolean read;

	@Builder
	public Notification(Long id, Long userId, String content, String url, LocalDateTime createdAt, boolean read) {
		this.id = id;
		this.userId = userId;
		this.content = content;
		this.url = url;
		this.createdAt = createdAt;
		this.read = read;
	}

	public void readNotification(){
		this.read=true;
	}
}
