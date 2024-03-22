package seongmin.minilife.common.response.exception;

import io.jsonwebtoken.JwtException;
import lombok.Getter;
import seongmin.minilife.common.response.code.AuthErrorCode;

@Getter
public class AuthErrorException extends JwtException {
    private final AuthErrorCode errorCode;
    private final String causedBy;

    public AuthErrorException(AuthErrorCode errorCode, String causedBy) {
        super(String.format("AuthErrorException(code=%s, message=%s, causedBy=%s)",
                errorCode.name(), errorCode.getMessage(), causedBy));
        this.errorCode = errorCode;
        this.causedBy = causedBy;
    }

    @Override
    public String toString() {
        return String.format("AuthErrorException(code=%s, message=%s, causedBy=%s)",
                errorCode.name(), errorCode.getMessage(), causedBy);
    }
}
