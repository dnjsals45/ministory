package seongmin.minilife.api.user.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import seongmin.minilife.common.jwt.dto.JwtTokenInfo;
import seongmin.minilife.domain.user.entity.User;
import seongmin.minilife.domain.user.repository.UserRepository;

import java.util.Map;

@Slf4j
@Service
public class UserService {
    private final UserRepository userRepository;
    private final String githubClientId;
    private final String githubClientSecret;
    private final String githubRedirectUri;
    private final String googleClientId;
    private final String googleClientSecret;
    private final String googleRedirectUri;

    public UserService(@Value("${spring.security.oauth2.client.registration.github.client-id}") String githubClientId,
                       @Value("${spring.security.oauth2.client.registration.github.client-secret}") String githubClientSecret,
                       @Value("${spring.security.oauth2.client.registration.github.redirect-uri}") String githubRedirectUri,
                       @Value("${spring.security.oauth2.client.registration.google.client-id}") String googleClientId,
                       @Value("${spring.security.oauth2.client.registration.google.client-secret}") String googleClientSecret,
                       @Value("${spring.security.oauth2.client.registration.google.redirect-uri}") String googleRedirectUri,
                       UserRepository userRepository) {
        this.githubClientId = githubClientId;
        this.githubClientSecret = githubClientSecret;
        this.githubRedirectUri = githubRedirectUri;
        this.googleClientId = googleClientId;
        this.googleClientSecret = googleClientSecret;
        this.googleRedirectUri = googleRedirectUri;
        this.userRepository = userRepository;
    }

    public String getCode(String provider) {
        String authorizeUrl;

        switch (provider) {
            case "github" -> authorizeUrl = getCodeFromGithub();
            case "google" -> authorizeUrl = getCodeFromGoogle();
            default -> throw new IllegalArgumentException("존재하지 않는 provider");
        }
        return authorizeUrl;
    }

    private String getCodeFromGoogle() {
        return String.format("https://accounts.google.com/o/oauth2/auth?client_id=%s&redirect_uri=%s&response_type=token&scope=https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/userinfo.profile", googleClientId, googleRedirectUri);
    }

    private String getCodeFromGithub() {
        return String.format("https://github.com/login/oauth/authorize?client_id=%s&redirect_uri=%s", githubClientId, githubRedirectUri);
    }

    public String getAccessTokenFromGithub(String authorizationCode) {
        String tokenUrl = "https://github.com/login/oauth/access_token";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Accept", "application/json");

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", githubClientId);
        body.add("client_secret", githubClientSecret);
        body.add("code", authorizationCode);
        body.add("redirect_uri", githubRedirectUri);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, httpHeaders);

        ResponseEntity<Map> responseEntity = restTemplate.exchange(
                tokenUrl,
                HttpMethod.POST,
                requestEntity,
                Map.class
        );

        Map<String, String> response = responseEntity.getBody();
        String accessToken = response.get("access_token");

        return accessToken;
    }

    public String getAccessTokenFromGoogle(String authorizationCode) {
        String tokenUrl = "https://oauth2.googleapis.com/token";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Accept", "application/json");

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", googleClientId);
        body.add("client_secret", googleClientSecret);
        body.add("code", authorizationCode);
        body.add("redirect_uri", googleRedirectUri);
        body.add("grant_type", "authorization_code");

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, httpHeaders);

        ResponseEntity<Map> responseEntity = restTemplate.exchange(
                tokenUrl,
                HttpMethod.POST,
                requestEntity,
                Map.class
        );

        Map<String, String> response = responseEntity.getBody();
        String accessToken = response.get("access_token");

        return accessToken;
    }

    public JwtTokenInfo loginWithGithub(String accessToken) {
        String apiUrl = "https://api.github.com/user";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Accept", "application/json");
        httpHeaders.setBearerAuth(accessToken);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, httpHeaders);

        ResponseEntity<Map> responseEntity = restTemplate.exchange(
                apiUrl,
                HttpMethod.GET,
                requestEntity,
                Map.class
        );

        Map<String, ?> response = responseEntity.getBody();
        String githubId = response.get("id").toString();

        User user = userRepository.findByOauthId(githubId).orElse(null); // orElse 구문에 setNewUser(response)을 넣었더니 왜 중복으로 계속 저장할까?
        if (user == null) { // 일단은 user == null 로 해결
            user = setGithubNewUser(response);
        }

        return JwtTokenInfo.from(user);
    }

    public JwtTokenInfo loginWithGoogle(String accessToken) {
        String apiUrl = "https://www.googleapis.com/oauth2/v3/userinfo";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Accept", "application/json");
        httpHeaders.setBearerAuth(accessToken);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, httpHeaders);

        ResponseEntity<Map> responseEntity = restTemplate.exchange(
                apiUrl,
                HttpMethod.GET,
                requestEntity,
                Map.class
        );

        Map<String, ?> response = responseEntity.getBody();
        String googleId = response.get("sub").toString();

        User user = userRepository.findByOauthId(googleId).orElse(null); // orElse 구문에 setNewUser(response)을 넣었더니 왜 중복으로 계속 저장할까?
        if (user == null) { // 일단은 user == null 로 해결
            user = setGoogleNewUser(response);
        }

        return JwtTokenInfo.from(user);
    }

    private User setGithubNewUser(Map<String, ?> response) {
        User newUser = User.builder()
                .email(response.get("login").toString() + "@github.com")
                .nickname(response.get("login").toString())
                .oauthId(response.get("id").toString())
                .oauthProvider("github")
                .role("ROLE_MEMBER")
                .build();

        userRepository.save(newUser);
        return newUser;
    }

    private User setGoogleNewUser(Map<String, ?> response) {
        String email = response.get("email").toString();
        User newUser = User.builder()
                .email(email)
                .nickname(email.substring(0, email.indexOf('@')))
                .oauthId(response.get("sub").toString())
                .oauthProvider("google")
                .role("ROLE_MEMBER")
                .build();

        userRepository.save(newUser);
        return newUser;
    }
}
