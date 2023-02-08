package instagram_clone.sgdevcamp_jikji_insta_clone_notification_server.sse.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

	@Id
	@GeneratedValue
	private Long id;

	private String content;

	public Notification(String name){
		this.content = name;
	}
}
