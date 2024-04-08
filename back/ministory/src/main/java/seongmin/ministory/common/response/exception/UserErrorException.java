package seongmin.ministory.common.response.exception;

import lombok.Getter;
import seongmin.ministory.common.response.code.UserErrorCode;

@Getter
public class UserErrorException extends RuntimeException {
    private final UserErrorCode errorCode;

    public UserErrorException(UserErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    @Override
    public String toString() {
        return String.format("UserErrorException(code=%s, message=%s)",
                errorCode.name(), errorCode.getMessage());
    }
}
