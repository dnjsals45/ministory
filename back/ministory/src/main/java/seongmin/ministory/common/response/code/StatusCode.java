package seongmin.ministory.common.response.code;

import org.springframework.http.HttpStatus;

public interface StatusCode {
    HttpStatus getHttpStatus();
    String getMessage();
}
