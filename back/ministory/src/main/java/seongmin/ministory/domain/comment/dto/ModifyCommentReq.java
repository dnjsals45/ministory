package seongmin.ministory.domain.comment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Schema(description = "댓글 수정 요청 DTO")
public class ModifyCommentReq {
    private String comment;
}
