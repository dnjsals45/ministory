package seongmin.ministory.domain.comment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import seongmin.ministory.domain.comment.entity.Comment;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Schema(description = "댓글 생성 응답 DTO")
public class CreateCommentRes {
    private Long commentId;
    private LocalDateTime createdAt;

    public static CreateCommentRes from(Comment comment) {
        return new CreateCommentRes(comment.getId(), comment.getCreatedAt());
    }
}
