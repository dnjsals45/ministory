package seongmin.minilife.domain.comment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Schema(description = "댓글 수정 응답 DTO")
public class ModifyCommentRes {
    private Long commentId;
    private LocalDateTime updatedAt;

    public static ModifyCommentRes of(Long commentId, LocalDateTime updatedAt) {
        return new ModifyCommentRes(commentId, updatedAt);
    }
}
