package seongmin.minilife.common.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import seongmin.minilife.common.auth.service.CustomUserDetailsService;
import seongmin.minilife.common.code.AuthErrorCode;
import seongmin.minilife.common.jwt.provider.TokenProvider;
import seongmin.minilife.common.response.ErrorResponse;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomJwtFilter extends OncePerRequestFilter {
    private final TokenProvider accessTokenProvider;
    private final CustomUserDetailsService customUserDetailsService;

    private final List<String> jwtIgnoreUrl = List.of(
            "/", "/favicon.ico",
            "/swagger", "/swagger-ui/**", "/v3/api-docs/**",
            "/oauth2/**", "/api/v1/users/**"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (ignoreTokenRequest(request)) {
            log.info("JWT Filter: Ignoring request: {}", request.getRequestURI());
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = resolveAccessToken(request, response);

        UserDetails userDetails = getUserDetails(accessToken);
        setAuthenticationUser(userDetails, request);

        filterChain.doFilter(request, response);
    }


    private boolean ignoreTokenRequest(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String method = request.getMethod();
        Optional<String> judge = jwtIgnoreUrl.stream()
                .filter(v ->
                        Pattern.matches(v.replace("**", ".*"), uri) ||
                                Pattern.matches(v.replace("/**", ""), uri)

                )
                .findFirst();
        return judge.isPresent() || "OPTIONS".equals(method);
    }

    private String resolveAccessToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String accessToken = accessTokenProvider.resolveToken(request);

        if (!StringUtils.hasText(accessToken)) {
            handleAuthErrorException(response, AuthErrorCode.EMPTY_ACCESS_TOKEN);
        }

        if (accessTokenProvider.isTokenExpire(accessToken)) {
            handleAuthErrorException(response, AuthErrorCode.EXPIRED_ACCESS_TOKEN);
        }

        return accessToken;
    }

    private UserDetails getUserDetails(String accessToken) {
        Long userId = accessTokenProvider.getIdFromToken(accessToken);
        return customUserDetailsService.loadUserByUsername(userId.toString());
    }

    private void setAuthenticationUser(UserDetails userDetails, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities()
        );

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("Authenticated user: {}", userDetails.getUsername());
    }

    /**
     * JwtFilter 내에서 에러 발생 시 에러 응답 반환
     */
    private void handleAuthErrorException(HttpServletResponse response, AuthErrorCode errorCode) throws IOException {
        response.setStatus(errorCode.getHttpStatus().value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        log.warn("JwtFilter Error: {}", errorCode.getMessage());

        String json = new ObjectMapper().writeValueAsString(ErrorResponse.of(errorCode.getMessage()));
        response.getWriter().write(json);
    }
}
