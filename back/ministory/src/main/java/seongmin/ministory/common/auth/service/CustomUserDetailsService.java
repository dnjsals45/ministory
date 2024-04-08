package seongmin.ministory.common.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import seongmin.ministory.api.user.service.UserUtilService;
import seongmin.ministory.common.auth.dto.CustomUserDetails;
import seongmin.ministory.domain.user.entity.User;

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
