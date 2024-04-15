package seongmin.ministory.common.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import seongmin.ministory.common.response.code.AuthErrorCode;
import seongmin.ministory.common.response.exception.AuthErrorException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtExceptionUtil {
    private static final Map<Class<? extends Exception>, AuthErrorCode> ERROR_CODE_MAP = new HashMap<>();

    static {
        ERROR_CODE_MAP.put(ExpiredJwtException.class, AuthErrorCode.EXPIRED_ACCESS_TOKEN);
        ERROR_CODE_MAP.put(MalformedJwtException.class, AuthErrorCode.MALFORMED_ACCESS_TOKEN);
        ERROR_CODE_MAP.put(UnsupportedJwtException.class, AuthErrorCode.UNSUPPORTED_JWT_TOKEN);
    }

    public static AuthErrorException determineAuthErrorException(Exception e) {
        return findAuthErrorException(e).orElseGet(
                () -> {
                    e.printStackTrace();
                    AuthErrorCode authErrorCode = ERROR_CODE_MAP.getOrDefault(e.getClass(), AuthErrorCode.UNEXPECTED_ERROR);
                    return new AuthErrorException(authErrorCode, authErrorCode.getMessage());
                }
        );
    }

    public static Optional<AuthErrorException> findAuthErrorException(Exception e) {
        if (e instanceof AuthErrorException) {
            return Optional.of((AuthErrorException) e);
        } else if (e.getCause() instanceof AuthErrorException) {
            return Optional.of((AuthErrorException) e.getCause());
        }
        return Optional.empty();
    }

}
