package instagram_clone.sgdevcamp_jikji_insta_clone_notification_server.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
	private String sender;
	private String receiver;
	private String contents;
	private String type;
}
