package seongmin.ministory.common.response.exception;

import lombok.Getter;
import seongmin.ministory.common.response.code.TagErrorCode;

@Getter
public class TagErrorException extends RuntimeException {
    private final TagErrorCode errorCode;

    public TagErrorException(TagErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    @Override
    public String toString() {
        return String.format("TagErrorException(code=%s, message=%s)",
                errorCode.name(), errorCode.getMessage());
    }
}
