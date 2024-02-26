package seongmin.minilife.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "API 응답 - 에러")
@BasicResponse
@Builder
public class ErrorResponse {
    private String status;
    private String message;

    public static ErrorResponse of(String message) {
        return ErrorResponse.builder()
                .status("error")
                .message(message)
                .build();
    }
}
