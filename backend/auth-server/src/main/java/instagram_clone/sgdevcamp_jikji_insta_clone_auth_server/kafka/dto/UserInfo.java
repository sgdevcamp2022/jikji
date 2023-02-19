package instagram_clone.sgdevcamp_jikji_insta_clone_auth_server.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
	private Integer userId;
	private String nickname;
}
