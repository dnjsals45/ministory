package seongmin.minilife.common.response.code;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum TagErrorCode implements StatusCode {
    /**
     * 400 BAD_REQUEST
     */
    INVALID_TAG(BAD_REQUEST, "존재하지 않는 태그입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
