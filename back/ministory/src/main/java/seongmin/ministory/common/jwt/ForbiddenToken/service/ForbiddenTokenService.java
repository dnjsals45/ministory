package seongmin.ministory.common.jwt.ForbiddenToken.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import seongmin.ministory.common.jwt.ForbiddenToken.dto.ForbiddenToken;
import seongmin.ministory.common.jwt.ForbiddenToken.repository.ForbiddenTokenRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class ForbiddenTokenService {
    private final ForbiddenTokenRepository forbiddenTokenRepository;

    @Value("${jwt.token.refresh-expiration}")
    private Long expiration;

    public void save(String refreshToken) {
        ForbiddenToken forbiddenToken = ForbiddenToken.builder()
                .token(refreshToken)
                .ttl(expiration / 1000)
                .build();
        forbiddenTokenRepository.save(forbiddenToken);
    }

    public boolean isExist(String refreshToken) {
        return forbiddenTokenRepository.existsByToken(refreshToken);
    }
}
