package seongmin.ministory.domain.comment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "댓글 생성 요청 DTO")
public class CreateCommentReq {
    private String comment;
}
