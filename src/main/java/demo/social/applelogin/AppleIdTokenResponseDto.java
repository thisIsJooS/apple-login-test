package demo.social.applelogin;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class AppleIdTokenResponseDto {
        private String code;
        private String id_token;
}
