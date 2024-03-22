package seongmin.minilife.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import seongmin.minilife.common.jwt.filter.CustomJwtExceptionFilter;
import seongmin.minilife.common.jwt.filter.CustomJwtFilter;

@Configuration
@RequiredArgsConstructor
public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private final CustomJwtFilter customJwtFilter;
    private final CustomJwtExceptionFilter customJwtExceptionFilter;

    @Override
    public void configure(HttpSecurity httpSecurity) {
        httpSecurity.addFilterBefore(customJwtFilter, UsernamePasswordAuthenticationFilter.class);
        httpSecurity.addFilterBefore(customJwtExceptionFilter, CustomJwtFilter.class);
    }

}
