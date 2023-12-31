package demo.social.applelogin;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppleLoginService {
    private final AppleLoginUtil appleUtils;
    private static ObjectMapper objectMapper = new ObjectMapper();

    @Value("${auth.apple.client.id}")
    private String AUD;

    @Value("${auth.apple.token.url}")
    private String AUTH_TOKEN_URL;

    @Value("${apple.refresh_token}")
    private String MY_REFRESH_TOKEN;

    /**
     * 유효한 id_token인 경우 client_secret 생성
     */
    public String getAppleClientSecret(String id_token) {

        if (appleUtils.verifyIdentityToken(id_token)) {
            return appleUtils.createClientSecret();
        }

        return null;
    }

    /**
     * id_token에서 payload 데이터 가져오기
     *
     * @return
     */
    public ApplePayload getPayload(String id_token) {
        return appleUtils.decodeFromIdToken(id_token);
    }

    /**
     * application.yml에 저장한 refresh token 불러오기
     * revoke 테스트용
     */
    public String getRefreshToken(){
        return MY_REFRESH_TOKEN;
    }
}
