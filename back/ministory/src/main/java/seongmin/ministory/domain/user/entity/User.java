package seongmin.ministory.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;
import seongmin.ministory.common.Auditing;

@Entity
@Table(name = "USER")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends Auditing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "nickname", nullable = false) // 기본적으로는 이메일 형식, 원한다면 변경 가능
    private String nickname;

    @Column(name = "oauth_id", nullable = false)
    private String oauthId;

    @Column(name = "oauth_provider", nullable = false)
    private String oauthProvider;

    @Column(name = "role", nullable = false)
    private String role;

}
