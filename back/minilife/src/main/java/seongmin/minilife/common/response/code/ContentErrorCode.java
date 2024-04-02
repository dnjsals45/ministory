package seongmin.minilife.common.response.code;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum ContentErrorCode implements StatusCode {
    /**
     * 400 BAD_REQUEST
     */
    DELETED_CONTENT(BAD_REQUEST, "삭제된 게시글입니다."),
    INVALID_CONTENT_ID(BAD_REQUEST, "존재하지 않는 게시글입니다."),
    EMPTY_IMAGE(BAD_REQUEST, "요청에 이미지가 없습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
