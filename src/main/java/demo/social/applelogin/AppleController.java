package demo.social.applelogin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@Slf4j
public class AppleController {
    private final AppleLoginService appleLoginService;
    private final AppleLoginUtil appleLoginUtil;

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
        log.error("================================");

        TokenResponse tokenResponse = appleLoginUtil.validateAuthorizationGrantCode(client_secret, serviceResponse.getCode());
        log.info("tokenResponse >> {}", tokenResponse);


        return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
    }
}
