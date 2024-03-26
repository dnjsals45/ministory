package seongmin.minilife.common.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthErrorResponse {
    private String status;
    private String message;

    public static AuthErrorResponse of(String message) {
        return AuthErrorResponse.builder()
                .status("error")
                .message(message)
                .build();
    }

    @Override
    public String toString() {
        return String.format("AuthErrorResponse(code=%s, message=%s)", status, message);
    }
}
