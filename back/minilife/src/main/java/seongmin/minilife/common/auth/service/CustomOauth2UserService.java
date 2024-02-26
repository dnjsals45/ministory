package seongmin.minilife.common.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import seongmin.minilife.common.auth.dto.CustomOAuth2User;
import seongmin.minilife.common.auth.dto.OAuthAttribute;
import seongmin.minilife.domain.user.entity.User;
import seongmin.minilife.domain.user.repository.UserRepository;

import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOauth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttribute oAuthAttribute = OAuthAttribute.of(userNameAttributeName, oAuth2User.getAttributes(), registrationId);
        User user = saveOrUpdate(oAuthAttribute);
        oAuthAttribute.updateUserId(user.getId());
        return new CustomOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRole())),
                oAuthAttribute.getAttributes(),
                oAuthAttribute.getNameAttributeKey(),
                oAuthAttribute.getUserId(),
                oAuthAttribute.getOauthId(),
                oAuthAttribute.getOauthProvider()
        );
    }

    private User saveOrUpdate(OAuthAttribute oAuthAttribute) {
        User user = userRepository.findByEmail(oAuthAttribute.getEmail())
                .orElse(oAuthAttribute.toEntity());
        if (!user.getOauthProvider().equals(oAuthAttribute.getOauthProvider())) {
            User otherAccount = User.builder()
                    .email(oAuthAttribute.getEmail())
                    .nickname(user.getNickname())
                    .oauthId(oAuthAttribute.getOauthId())
                    .oauthProvider(oAuthAttribute.getOauthProvider())
                    .role(user.getRole())
                    .build();
            return userRepository.save(otherAccount);
        }
        return userRepository.save(user);
    }
}
