package seongmin.ministory.common.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@Data
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    private Secret secret;
    private Token token;

    @Data
    public static class Secret {
        private String accessSecretKey;
        private String refreshSecretKey;
    }

    @Data
    public static class Token {
        private Duration accessExpiration;
        private String accessHeader;
        private Duration refreshExpiration;
    }
}
