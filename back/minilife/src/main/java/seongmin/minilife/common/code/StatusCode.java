package seongmin.minilife.common.code;

import org.springframework.http.HttpStatus;

public interface StatusCode {
    HttpStatus getHttpStatus();
    String getMessage();
}
