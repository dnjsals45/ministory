package seongmin.minilife.domain.content.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import seongmin.minilife.domain.content.entity.Content;
import seongmin.minilife.domain.user.entity.User;

@Getter
@AllArgsConstructor
public class GetContentRes {
    private ResContent content;
    private ResUser user;

    @Getter
    @AllArgsConstructor
    public static class ResContent {
        private String title;
        private String body;
        private Boolean complete;
        private Long views;

        public static ResContent from(Content content) {
            return new ResContent(content.getTitle(), content.getBody(), content.getComplete(), content.getViews());
        }
    }

    @Getter
    @AllArgsConstructor
    public static class ResUser {
        private String nickname;

        public static ResUser from(User user) {
            return new ResUser(user.getNickname());
        }
    }

    public static GetContentRes from(Content content) {
        return new GetContentRes(ResContent.from(content), ResUser.from(content.getUser()));
    }
}
