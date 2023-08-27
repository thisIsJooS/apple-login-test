package demo.social.applelogin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;


@RestController
@RequiredArgsConstructor
@Slf4j
public class AppleController {
    private final AppleLoginService appleLoginService;
    private final AppleLoginUtil appleLoginUtil;

    @Value("${auth.apple.client.id}")
    private String AUD;

    /**
     * redirect URL, 애플은 post 요청으로 body에 정보를 담아서 보내준다.
     */
    @PostMapping("/callback")
    public ResponseEntity<?> appleRedirect(AppleIdTokenResponseDto serviceResponse) throws Exception{
        if(serviceResponse == null){
            return null;
        }

        String client_secret = appleLoginService.getAppleClientSecret(serviceResponse.getId_token());
        log.error("================================");
        log.error("id_token ‣ " + serviceResponse.getId_token());
        log.error("payload ‣ " + appleLoginService.getPayload(serviceResponse.getId_token()));
        log.error("client_secret ‣ " + client_secret);
        AppleTokenResponse appleTokenResponse = appleLoginUtil.validateAuthorizationGrantCode(client_secret, serviceResponse.getCode());
        log.info("tokenResponse >> {}", appleTokenResponse);
        log.info("validate refresh token >> {}", appleLoginUtil.validateAnExistingRefreshToken(client_secret, appleTokenResponse.getRefresh_token()));
        log.error("================================");



        return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
    }

    /**
     * 회원탈퇴
     */
    @PostMapping("/revoke")
    public ResponseEntity<?> revoke(){
        String refreshToken = appleLoginService.getRefreshToken();
        String clientSecret = appleLoginUtil.createClientSecret();
        AppleTokenResponse appleTokenResponse = appleLoginUtil.validateAnExistingRefreshToken(clientSecret, refreshToken);
        String accessToken = appleTokenResponse.getAccess_token();

        RestTemplate restTemplate = new RestTemplateBuilder().build();
        String revokeUrl = "https://appleid.apple.com/auth/revoke";

        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", AUD);
        params.add("client_secret", clientSecret);
        params.add("token", accessToken);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);

         return restTemplate.postForEntity(revokeUrl, httpEntity, String.class);

    }
}
