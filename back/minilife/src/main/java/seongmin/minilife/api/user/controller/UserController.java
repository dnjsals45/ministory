package seongmin.minilife.api.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import seongmin.minilife.api.user.service.UserService;
import seongmin.minilife.api.user.service.UserUtilService;
import seongmin.minilife.common.jwt.dto.JwtTokenInfo;
import seongmin.minilife.common.jwt.provider.TokenProvider;
import seongmin.minilife.common.response.SuccessResponse;
import seongmin.minilife.domain.user.entity.User;

import java.io.IOException;

@Tag(name = "유저 API", description = "유저 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final TokenProvider accessTokenProvider;
    private final UserService userService;
    private final UserUtilService userUtilService;

    @Operation(summary = "테스트용 로그인", description = "DB에 있는 유저의 id만을 이용해서 로그인해야 가능한 기능 테스트")
    @GetMapping("/{user_id}")
    public ResponseEntity<?> login(@Parameter(name = "user_id", description = "유저 회원번호")
                                   @PathVariable(name = "user_id") Long userId) {
        User user = userUtilService.findById(userId);

        String accessToken = userUtilService.login(user);

        return ResponseEntity.noContent()
                .header("Authorization", "Bearer " + accessToken)
                .build();
    }

    @Operation(summary = "Oauth 로그인/회원가입", description = "각 Provider 의 인증 코드를 가져옴")
    @GetMapping("/login/{provider}")
    public void getCode(@Parameter(name = "provider", description = "google, github")
                        @PathVariable(name = "provider") String provider,
                        HttpServletResponse response) throws IOException {
        String url = userService.getCode(provider);
        System.out.println("url = " + url);
        response.sendRedirect(url);
    }

    @GetMapping("/auth/{provider}")
    @PreAuthorize("isAnonymous()")
    public ResponseEntity<?> login(@Parameter(name = "provider", description = "google, github")
                                   @PathVariable(name = "provider") String provider,
                                   @RequestParam("code") String authorizationCode) {
        String authorizationToken;
        switch (provider) {
            case "github" -> authorizationToken = userService.getAccessTokenFromGithub(authorizationCode);
            case "google" -> authorizationToken = userService.getAccessTokenFromGoogle(authorizationCode);
            default -> throw new IllegalArgumentException("존재하지 않는 provider");
        }
        JwtTokenInfo tokenInfo;
        switch (provider) {
            case "github" -> tokenInfo = userService.loginWithGithub(authorizationToken);
            case "google" -> tokenInfo = userService.loginWithGoogle(authorizationToken);
            default -> throw new IllegalArgumentException("존재하지 않는 provider");
        }
        String accessToken = accessTokenProvider.generateToken(tokenInfo);


        return ResponseEntity.ok().header("Access-Token", accessToken).body(SuccessResponse.noContent());
    }
}
