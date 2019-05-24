package cl.queltehues.api.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {

    @Value(value = "${security.authentication.jwt.base64-secret}")
    private String secret;
    @Value(value = "${security.authentication.jwt.token-validity-inseconds}")
    private int tokenValidityInSeconds;
    @Value(value = "${security.authentication.jwt.token-validity-in-seconds-for-remember-me}")
    private int tokenValidityInSecondsForRememberMe;

    public String getSecret() {
        return secret;
    }

    public int getTokenValidityInSeconds() {
        return tokenValidityInSeconds;
    }

    public int getTokenValidityInSecondsForRememberMe() {
        return tokenValidityInSecondsForRememberMe;
    }
}
