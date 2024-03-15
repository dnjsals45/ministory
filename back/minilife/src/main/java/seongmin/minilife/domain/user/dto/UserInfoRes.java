package seongmin.minilife.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import seongmin.minilife.domain.user.entity.User;

@Getter
@AllArgsConstructor
public class UserInfoRes {
    private String email;
    private String nickname;
    private String role;

    public static UserInfoRes from(User user) {
        return new UserInfoRes(user.getEmail(), user.getNickname(), user.getRole());
    }
}
