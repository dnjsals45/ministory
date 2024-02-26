package seongmin.minilife.common.auth.handler;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import seongmin.minilife.common.auth.dto.CustomOAuth2User;
import seongmin.minilife.common.jwt.dto.JwtTokenInfo;
import seongmin.minilife.common.jwt.provider.AccessTokenProvider;
import seongmin.minilife.common.jwt.provider.TokenProvider;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final TokenProvider accessTokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        log.info("Oauth2 login success: User@{}", oAuth2User.getUserId());
        JwtTokenInfo tokenInfo = JwtTokenInfo.from(oAuth2User);

        String accessToken = accessTokenProvider.generateToken(tokenInfo);

//        response.sendRedirect("http://localhost:3000");
    }
}
