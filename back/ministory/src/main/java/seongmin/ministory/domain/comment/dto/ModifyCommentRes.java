package seongmin.ministory.domain.comment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "댓글 수정 응답 DTO")
public class ModifyCommentRes {
    private Long commentId;
    private LocalDateTime updatedAt;

    public static ModifyCommentRes of(Long commentId, LocalDateTime updatedAt) {
        return new ModifyCommentRes(commentId, updatedAt);
    }
}
