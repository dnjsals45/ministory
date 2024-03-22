package seongmin.minilife.common.response;

import lombok.Getter;

@Getter
public class AuthErrorResponse {
    private String errorCode;
    private String message;

    public AuthErrorResponse(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    @Override
    public String toString() {
        return String.format("AuthErrorResponse(code=%s, message=%s)", errorCode, message);
    }
}
