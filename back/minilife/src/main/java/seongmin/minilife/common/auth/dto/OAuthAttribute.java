package seongmin.minilife.common.auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import seongmin.minilife.domain.user.entity.User;

import java.util.Map;

@Slf4j
@Getter
public class OAuthAttribute {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private Long userId;
    private String email;
    private String nickname;
    private String oauthId;
    private String oauthProvider;

    @Builder
    public OAuthAttribute(Map<String, Object> attributes, String nameAttributeKey, Long userId, String email, String nickname, String oauthId, String oauthProvider) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.userId = userId;
        this.email = email;
        this.nickname = nickname;
        this.oauthId = oauthId;
        this.oauthProvider = oauthProvider;
    }

    public static OAuthAttribute of(String userNameAttributeName, Map<String, Object> attributes, String registrationId) {
        return switch (registrationId) {
            case "github" -> ofGithub(userNameAttributeName, attributes);
            case "google" -> ofGoogle(userNameAttributeName, attributes);
            default -> null;
        };
    }

    public static OAuthAttribute ofGithub(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttribute.builder()
                .email(attributes.get("login").toString() + "@github.com")
                .nickname(attributes.get("login").toString())
                .oauthId(attributes.get("id").toString())
                .oauthProvider("GITHUB")
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttribute ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        String email = attributes.get("email").toString();
        return OAuthAttribute.builder()
                .email(email)
                .nickname(email.substring(0, email.indexOf('@')))
                .oauthId(attributes.get(userNameAttributeName).toString())
                .oauthProvider("GOOGLE")
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }


//    public OAuthAttribute updateUserId(Long userId) {
//        this.userId = userId;
//        return this;
//    }

    public User toEntity() {
        return User.builder()
                .email(email)
                .nickname(nickname)
                .oauthId(oauthId)
                .oauthProvider(oauthProvider)
                .role("ROLE_MEMBER")
                .build();
    }
}
