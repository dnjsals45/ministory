package seongmin.minilife.common.response.code;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum CommentErrorCode implements StatusCode {
    /**
     * 400 BAD_REQUEST
     */
    DELETED_COMMENT(BAD_REQUEST, "삭제된 댓글입니다."),
    INVALID_COMMENT_ID(BAD_REQUEST, "존재하지 않는 댓글입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
