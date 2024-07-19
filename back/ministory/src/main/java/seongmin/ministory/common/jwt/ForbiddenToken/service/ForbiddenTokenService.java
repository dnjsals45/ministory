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

    public void save(String token, Long expiration) {
        ForbiddenToken forbiddenToken = ForbiddenToken.builder()
                .token(token)
                .ttl(expiration / 1000)
                .build();
        forbiddenTokenRepository.save(forbiddenToken);
    }

    public boolean isExist(String token) {
        return forbiddenTokenRepository.existsByToken(token);
    }
}
