package seongmin.ministory.domain.content.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import seongmin.ministory.domain.content.entity.Content;
import seongmin.ministory.domain.tag.dto.ContentTagDto;
import seongmin.ministory.domain.tag.entity.ContentTag;
import seongmin.ministory.domain.user.entity.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class GetContentRes {
    private ResContent content;
    private ResUser user;
    @Getter
    @AllArgsConstructor
    @Builder
    public static class ResContent {
        private Long contentId;
        private String title;
        private String body;
        private Boolean complete;
        private Long views;
        private LocalDateTime createdAt;
        private List<ContentTagDto> tags;


        public static ResContent from(Content content) {
            List<ContentTagDto> response = new ArrayList<>();
            List<ContentTag> tags = content.getContentTags();
            for (ContentTag tag : tags) {
                response.add(ContentTagDto.from(tag));
            }

            return ResContent.builder()
                    .contentId(content.getId())
                    .title(content.getTitle())
                    .body(content.getBody())
                    .complete(content.getComplete())
                    .views(content.getViews())
                    .createdAt(content.getCreatedAt())
                    .tags(response)
                    .build();
        }

        public static ResContent from(Content content, String body) {
            String limited;
            if (body.length() >= 200) {
                limited = body.substring(0, 197) + "...";
            } else {
                limited = body;
            }
            List<ContentTagDto> response = new ArrayList<>();
            List<ContentTag> tags = content.getContentTags();
            for (ContentTag tag : tags) {
                response.add(ContentTagDto.from(tag));
            }

            return ResContent.builder()
                    .contentId(content.getId())
                    .title(content.getTitle())
                    .body(limited)
                    .complete(content.getComplete())
                    .views(content.getViews())
                    .createdAt(content.getCreatedAt())
                    .tags(response)
                    .build();
        }
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class ResUser {
        private String nickname;

        public static ResUser from(User user) {
            return ResUser.builder()
                    .nickname(user.getNickname())
                    .build();
        }
    }

    public static GetContentRes from(Content content) {
        List<ContentTagDto> response = new ArrayList<>();
        List<ContentTag> tags = content.getContentTags();
        for (ContentTag tag : tags) {
            response.add(ContentTagDto.from(tag));
        }

        return GetContentRes.builder()
                .content(ResContent.from(content))
                .user(ResUser.from(content.getUser()))
                .build();
    }

    public static GetContentRes from(Content content, String body) {
        List<ContentTagDto> response = new ArrayList<>();
        List<ContentTag> tags = content.getContentTags();
        for (ContentTag tag : tags) {
            response.add(ContentTagDto.from(tag));
        }

        return GetContentRes.builder()
                .content(ResContent.from(content, body))
                .user(ResUser.from(content.getUser()))
                .build();
    }
}
