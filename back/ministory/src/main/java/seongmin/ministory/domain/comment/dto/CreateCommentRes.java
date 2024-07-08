package seongmin.ministory.domain.comment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import seongmin.ministory.domain.comment.entity.Comment;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "댓글 생성 응답 DTO")
public class CreateCommentRes {
    private Long commentId;
    private LocalDateTime createdAt;

    public static CreateCommentRes from(Comment comment) {
        return new CreateCommentRes(comment.getId(), comment.getCreatedAt());
    }
}
