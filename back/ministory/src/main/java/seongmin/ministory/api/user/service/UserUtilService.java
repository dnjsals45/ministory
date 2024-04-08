package seongmin.ministory.api.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import seongmin.ministory.common.jwt.dto.JwtTokenInfo;
import seongmin.ministory.common.jwt.provider.TokenProvider;
import seongmin.ministory.common.response.code.UserErrorCode;
import seongmin.ministory.common.response.exception.UserErrorException;
import seongmin.ministory.domain.user.entity.User;
import seongmin.ministory.domain.user.repository.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserUtilService {
    private final TokenProvider accessTokenProvider;
    private final UserRepository userRepository;

    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserErrorException(UserErrorCode.USER_NOT_FOUND));
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
