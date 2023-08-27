package demo.social.applelogin;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AppleTokenResponse {
    private String access_token;
    private Long expires_in;
    private String id_token;
    private String refresh_token;
    private String token_type;
}
