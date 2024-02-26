package seongmin.minilife.api.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import seongmin.minilife.common.jwt.dto.JwtTokenInfo;
import seongmin.minilife.common.jwt.provider.TokenProvider;
import seongmin.minilife.domain.user.entity.User;
import seongmin.minilife.domain.user.repository.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserUtilService {
    private final TokenProvider accessTokenProvider;
    private final UserRepository userRepository;

    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 유저입니다."));
    }

    public String login(User user) {
        return generateToken(JwtTokenInfo.from(user));
    }

    private String generateToken(JwtTokenInfo tokenInfo) {
        String accessToken = accessTokenProvider.generateToken(tokenInfo);

        log.warn("new AccessToken provided: {}", accessToken);
        return accessToken;
    }
}
