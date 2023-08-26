package demo.social.applelogin;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class AppleIdTokenResponseDto {
        private String state;
        private String code;
        private String id_token;
        private String user;
}
