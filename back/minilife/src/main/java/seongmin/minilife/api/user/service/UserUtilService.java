package seongmin.minilife.api.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import seongmin.minilife.domain.user.entity.User;
import seongmin.minilife.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserUtilService {
    private final UserRepository userRepository;

    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 유저입니다."));
    }
}
