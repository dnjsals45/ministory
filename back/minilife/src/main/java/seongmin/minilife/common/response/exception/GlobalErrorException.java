package seongmin.minilife.common.response.exception;

import lombok.Getter;
import seongmin.minilife.common.response.code.GlobalErrorCode;

@Getter
public class GlobalErrorException extends RuntimeException {
    private final GlobalErrorCode errorCode;

    public GlobalErrorException(GlobalErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    @Override
    public String toString() {
        return String.format("GlobalErrorException(code=%s, message=%s)",
                errorCode.name(), errorCode.getMessage());
    }
}
