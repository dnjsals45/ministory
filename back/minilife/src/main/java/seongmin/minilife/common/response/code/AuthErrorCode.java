package seongmin.minilife.common.response.code;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum AuthErrorCode implements StatusCode {
    /**
     * 400 BAD_REQUEST
     */
    EMPTY_ACCESS_TOKEN(BAD_REQUEST, "액세스 토큰이 비어있습니다."),

    /**
     * 401 UNAUTHORIZED
     */
    FAILED_AUTHENTICATION(UNAUTHORIZED, "인증에 실패하였습니다"),
    TAMPERED_ACCESS_TOKEN(UNAUTHORIZED, "서명이 조작된 토큰입니다"),
    EXPIRED_ACCESS_TOKEN(UNAUTHORIZED, "사용기간이 만료된 토큰입니다"),
    MALFORMED_ACCESS_TOKEN(UNAUTHORIZED, "비정상적인 토큰입니다"),
    WRONG_JWT_TOKEN(UNAUTHORIZED, "잘못된 토큰입니다(default)"),
    REFRESH_TOKEN_NOT_FOUND(UNAUTHORIZED, "없거나 삭제된 리프래시 토큰입니다."),
    UNSUPPORTED_JWT_TOKEN(UNAUTHORIZED, "지원하지 않는 토큰입니다"),

    /**
     * 500 INTERNAL_SERVER_ERROR
     */
    UNEXPECTED_ERROR(INTERNAL_SERVER_ERROR, "예상치 못한 에러가 발생했습니다.");
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
