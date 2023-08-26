package demo.social.applelogin;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AppleIdTokenResponseDto {
        private String state;
        private String code;
        private String id_token;
        private String user;
}
