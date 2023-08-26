package demo.social.applelogin;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AppleController {
    @GetMapping("/1")
    public String d(){
        return "dd";
    }
}
