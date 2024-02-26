package seongmin.minilife.common.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import seongmin.minilife.api.user.service.UserUtilService;
import seongmin.minilife.common.auth.dto.CustomUserDetails;
import seongmin.minilife.domain.user.entity.User;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserUtilService userUtilService;


    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = userUtilService.findById(Long.parseLong(userId));

        return CustomUserDetails.of(user);
    }
}
