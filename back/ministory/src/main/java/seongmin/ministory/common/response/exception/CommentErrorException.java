package seongmin.ministory.common.response.exception;

import lombok.Getter;
import seongmin.ministory.common.response.code.CommentErrorCode;

@Getter
public class CommentErrorException extends RuntimeException {
    private final CommentErrorCode errorCode;

    public CommentErrorException(CommentErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    @Override
    public String toString() {
        return String.format("CommentErrorException(code=%s, message=%s)",
                errorCode.name(), errorCode.getMessage());
    }
}
