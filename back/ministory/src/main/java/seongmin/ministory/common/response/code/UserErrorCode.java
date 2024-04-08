package seongmin.ministory.common.response.code;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum UserErrorCode implements StatusCode {
    /**
     * 400 BAD_QUEST: Client 요청 오류
     */
    USER_NOT_FOUND(BAD_REQUEST, "유저가 존재하지 않습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
