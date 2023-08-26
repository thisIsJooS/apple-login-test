package demo.social.applelogin;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@NoArgsConstructor
@Setter
@ToString
public class AppleIdTokenResponseDto {
        private String code;
        private String id_token;
}
