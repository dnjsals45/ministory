package seongmin.ministory.common.jwt.filter;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import seongmin.ministory.api.user.service.UserUtilService;
import seongmin.ministory.common.auth.dto.CustomUserDetails;
import seongmin.ministory.common.jwt.ForbiddenToken.service.ForbiddenTokenService;
import seongmin.ministory.common.jwt.dto.JwtTokenInfo;
import seongmin.ministory.common.response.code.AuthErrorCode;
import seongmin.ministory.common.response.exception.AuthErrorException;
import seongmin.ministory.common.auth.service.CustomUserDetailsService;
import seongmin.ministory.common.jwt.provider.TokenProvider;
import seongmin.ministory.domain.user.entity.User;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomJwtFilter extends OncePerRequestFilter {
    private final TokenProvider accessTokenProvider;
    private final TokenProvider refreshTokenProvider;
    private final CustomUserDetailsService customUserDetailsService;
    private final UserUtilService userUtilService;
    private final ForbiddenTokenService forbiddenTokenService;

    private final List<String> jwtIgnoreUrl = List.of(
            "/", "/favicon.ico",
            "/swagger", "/swagger-ui/**", "/v3/api-docs/**",
            "/oauth2/**"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        setViewerId(request, response);

        if (ignoreTokenRequest(request)) {
            log.info("JWT Filter: Ignoring request: {}", request.getRequestURI());
            filterChain.doFilter(request, response);
            return;
        }

        if (isAnonymousRequest(request)) {
            log.info("JWT Filter: Anonymous request: {}", request.getRequestURI());
            filterChain.doFilter(request, response);
            return;
        }

        log.info("JWT Filter: request: {}", request.getRequestURI());

        try {
            String accessToken = resolveAccessToken(request);

            if (forbiddenTokenService.isExist(accessToken)) {
                handleAuthErrorException(AuthErrorCode.INVALID_REFRESH_TOKEN, "유효하지 않은 토큰입니다.");
            }

            CustomUserDetails userDetails = (CustomUserDetails) getUserDetails(accessToken);
            setAuthenticationUser(userDetails, request);
        } catch (ExpiredJwtException e1) {
            try {
                String refreshToken = resolveCookie(request, "Refresh-Token");

                if (forbiddenTokenService.isExist(refreshToken)) {
                    handleAuthErrorException(AuthErrorCode.INVALID_REFRESH_TOKEN, "유효하지 않은 토큰입니다.");
                }

                Long userId = refreshTokenProvider.getIdFromToken(refreshToken);
                User user = userUtilService.findById(userId);
                String newAccessToken = accessTokenProvider.generateToken(JwtTokenInfo.from(user));
                response.setHeader("Access-Token", newAccessToken);
                CustomUserDetails userDetails = (CustomUserDetails) getUserDetails(newAccessToken);
                setAuthenticationUser(userDetails, request);
                filterChain.doFilter(request, response);

                return;
            } catch (ExpiredJwtException e2) {
                handleAuthErrorException(AuthErrorCode.NEED_LOGIN, "로그인이 필요합니다.");
            }
        }

        filterChain.doFilter(request, response);
    }

    private void setViewerId(HttpServletRequest request, HttpServletResponse response) {
        String viewerId = resolveCookie(request, "Viewer-Id");

        if (!StringUtils.hasText(viewerId)) {
            viewerId = generateNewViewerId();

            ResponseCookie viewerIdCookie = ResponseCookie.from("Viewer-Id", viewerId)
                    .httpOnly(true)
                    .path("/")
                    .maxAge(getUntilTomorrow())
                    .secure(true)
                    .sameSite("None")
                    .build();

            response.addHeader(HttpHeaders.SET_COOKIE, viewerIdCookie.toString());
        }

        request.setAttribute("viewerId", viewerId);
    }

    private static long getUntilTomorrow() {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        LocalDateTime tomorrow = now.plusDays(1).truncatedTo(ChronoUnit.DAYS);
        return ChronoUnit.SECONDS.between(now, tomorrow);
    }

    private String generateNewViewerId() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    private boolean isAnonymousRequest(HttpServletRequest request) {
        String accessToken = request.getHeader("Authorization");

        return !StringUtils.hasText(accessToken);
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

    private String resolveAccessToken(HttpServletRequest request) throws ServletException {
        String accessToken = accessTokenProvider.resolveToken(request);

        if (!StringUtils.hasText(accessToken)) {
            handleAuthErrorException(AuthErrorCode.EMPTY_ACCESS_TOKEN, "엑세스 토큰이 비어있습니다.");
        }

        return accessToken;
    }

    private String resolveCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookieName.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    private UserDetails getUserDetails(String accessToken) {
        Long userId = accessTokenProvider.getIdFromToken(accessToken);
        return customUserDetailsService.loadUserByUsername(userId.toString());
    }

    private void setAuthenticationUser(CustomUserDetails userDetails, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities()
        );

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("Authenticated user: {}", userDetails.getUserId());
    }

    /**
     * JwtFilter 내에서 에러 발생 시 에러 응답 반환
     */
    private void handleAuthErrorException(AuthErrorCode errorCode, String errorMessage) throws ServletException {
        log.warn("AuthErrorException: {}@{}", errorCode.name(), errorMessage);
        AuthErrorException exception = new AuthErrorException(errorCode, errorMessage);
        throw new ServletException(exception);
    }
}
