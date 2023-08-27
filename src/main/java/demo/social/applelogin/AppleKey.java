package demo.social.applelogin;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppleKey {

    private String kty;
    private String kid;
    private String use;
    private String alg;
    private String n;
    private String e;
}