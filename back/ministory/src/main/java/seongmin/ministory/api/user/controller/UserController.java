package seongmin.ministory.api.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import seongmin.ministory.api.user.service.UserService;
import seongmin.ministory.api.user.service.UserUtilService;
import seongmin.ministory.common.auth.dto.CustomUserDetails;
import seongmin.ministory.common.jwt.dto.JwtTokenInfo;
import seongmin.ministory.common.jwt.provider.TokenProvider;
import seongmin.ministory.common.response.SuccessResponse;
import seongmin.ministory.common.response.code.AuthErrorCode;
import seongmin.ministory.common.response.exception.AuthErrorException;
import seongmin.ministory.domain.user.entity.User;

import java.io.IOException;

@Tag(name = "유저 API", description = "유저 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final TokenProvider accessTokenProvider;
    private final TokenProvider refreshTokenProvider;
    private final UserService userService;
    private final UserUtilService userUtilService;

    @Operation(summary = "테스트용 로그인", description = "DB에 있는 유저의 id만을 이용해서 로그인해야 가능한 기능 테스트")
    @GetMapping("/{user_id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> login(@Parameter(name = "user_id", description = "유저 회원번호")
                                   @PathVariable(name = "user_id") Long userId) {
        User user = userUtilService.findById(userId);

        String accessToken = userUtilService.login(user);

        return ResponseEntity.noContent()
                .header("Acess-Token", accessToken)
                .build();
    }

    @Operation(summary = "Oauth 로그인/회원가입", description = "각 Provider 의 인증 코드를 가져옴")
    @GetMapping("/login/{provider}")
    @PreAuthorize("permitAll()")
    public void getCode(@Parameter(name = "provider", description = "google, github")
                        @PathVariable(name = "provider") String provider,
                        HttpServletResponse response) throws IOException {
        String url = userService.getCode(provider);
        response.sendRedirect(url);
    }

    @GetMapping("/auth/{provider}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> login(@Parameter(name = "provider", description = "google, github")
                                   @PathVariable(name = "provider") String provider,
                                   @RequestParam("code") String authorizationCode,
                                   HttpServletResponse response) {
        String authorizationToken;
        log.info("Login with {}", provider);
        switch (provider) {
            case "github" -> authorizationToken = userService.getAccessTokenFromGithub(authorizationCode);
            case "google" -> authorizationToken = userService.getAccessTokenFromGoogle(authorizationCode);
            default -> throw new AuthErrorException(AuthErrorCode.UNKNOWN_PROVIDER, "존재하지 않는 프로바이더 입니다.");
        }
        JwtTokenInfo tokenInfo;
        switch (provider) {
            case "github" -> tokenInfo = userService.loginWithGithub(authorizationToken);
            case "google" -> tokenInfo = userService.loginWithGoogle(authorizationToken);
            default -> throw new AuthErrorException(AuthErrorCode.UNKNOWN_PROVIDER, "존재하지 않는 프로바이더 입니다.");
        }
        String accessToken = accessTokenProvider.generateToken(tokenInfo);
        String refreshToken = refreshTokenProvider.generateToken(tokenInfo);

        ResponseCookie refreshTokenCookie = ResponseCookie.from("Refresh-Token", refreshToken)
                .httpOnly(true)
                .path("/")
                .maxAge(60 * 60 * 24)
                .secure(true)
                .sameSite("None")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());

        return ResponseEntity.ok().header("Access-Token", accessToken).body(SuccessResponse.noContent());
    }

    @Operation(summary = "유저 정보 확인", description = "AT를 통해 유저 정보를 확인하여 반환한다")
    @GetMapping("")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getUserInfo(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok().body(SuccessResponse.from(userService.getUserInfo(userDetails)));
    }

    @Operation(summary = "로그아웃", description = "쿠키 무효화")
    @GetMapping("/logout")
    public ResponseEntity<?> logOut(HttpServletResponse response) {

        ResponseCookie refreshTokenCookie = ResponseCookie.from("Refresh-Token", "")
                .httpOnly(true)
                .path("/")
                .maxAge(0)
                .secure(true)
                .sameSite("None")
                .build();


        response.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());

        return ResponseEntity.ok().body(SuccessResponse.noContent());
    }
}
