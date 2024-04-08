package seongmin.ministory.common.jwt.dto;

import lombok.Builder;
import lombok.Getter;
import seongmin.ministory.common.auth.dto.CustomOAuth2User;
import seongmin.ministory.domain.user.entity.User;

@Getter
@Builder
public class JwtTokenInfo {
    private Long userId;
    private String role;
    private String oauthId;
    private String oauthProvider;

    public static JwtTokenInfo from(CustomOAuth2User oAuth2User) {
        return JwtTokenInfo.builder()
                .userId(oAuth2User.getUserId())
                .role(oAuth2User.getAuthorities().iterator().next().getAuthority().substring("ROLE_".length()))
                .oauthId(oAuth2User.getOauthId())
                .oauthProvider(oAuth2User.getOauthProvider())
                .build();
    }

    public static JwtTokenInfo from(User user) {
        return JwtTokenInfo.builder()
                .userId(user.getId())
                .role(user.getRole())
                .oauthId(user.getOauthId())
                .oauthProvider(user.getOauthProvider())
                .build();
    }
}
