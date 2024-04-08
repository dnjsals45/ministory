package seongmin.ministory.domain.comment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import seongmin.ministory.domain.comment.entity.Comment;
import seongmin.ministory.domain.user.entity.User;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
@Schema(description = "댓글 조회 응답 DTO")
public class GetCommentRes {
    private ResComment comment;
    private ResUser user;

    @Getter
    @AllArgsConstructor
    public static class ResComment {
        private String comment;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private LocalDateTime deletedAt;

        public static ResComment from(Comment comment) {
            return new ResComment(comment.getComment(), comment.getCreatedAt(), comment.getUpdatedAt(), comment.getDeletedAt());
        }
    }

    @Getter
    @AllArgsConstructor
    public static class ResUser {
        private String nickname;

        public static GetCommentRes.ResUser from(User user) {
            return new GetCommentRes.ResUser(user.getNickname());
        }
    }

    public static GetCommentRes fromEntity(Comment comment) {
        return GetCommentRes.builder()
                .comment(ResComment.from(comment))
                .user(ResUser.from(comment.getUser()))
                .build();
    }
}
