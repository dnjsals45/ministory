package seongmin.ministory.domain.content.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "게시글 수정 요청 DTO")
public class PostContentReq {
    @NotBlank
    @Schema(description = "게시글 제목", example = "제목")
    private String title;

    @NotNull
    @Schema(description = "게시글 내용", example = "내용")
    private String body;

    @NotNull
    @Schema(description = "작성 완료 여부", example = "false")
    private Boolean complete;

    @Schema(description = "태그 목록")
    private List<String> tags;
}
