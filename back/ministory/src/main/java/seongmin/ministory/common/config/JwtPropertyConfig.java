package seongmin.ministory.common.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import seongmin.ministory.common.jwt.JwtProperties;
import seongmin.ministory.common.jwt.provider.AccessTokenProvider;

@Configuration
@EnableConfigurationProperties(JwtProperties.class)
public class JwtPropertyConfig {

    @Bean(name = "accessTokenProvider")
    public AccessTokenProvider accessTokenProvider(JwtProperties jwtProperties) {
        return new AccessTokenProvider (
                jwtProperties.getSecret().getAccessSecretKey(),
                jwtProperties.getToken().getAccessExpiration()
        );
    }
}
