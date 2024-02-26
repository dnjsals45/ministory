package seongmin.minilife.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "API 응답 - 성공")
@BasicResponse
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SuccessResponse<T> {
    @Schema(description = "응답 상태", defaultValue = "success")
    private final String status = "success";
    @Schema(description = "응답 코드", example = "200")
    private T data;

    @Builder
    private SuccessResponse(T data) {
        this.data = data;
    }

    public static <T> SuccessResponse<T> from(T data) {
        return SuccessResponse.<T>builder()
                .data(data)
                .build();
    }

    public static SuccessResponse<?> noContent() {
        return SuccessResponse.builder().build();
    }
}
