package seongmin.ministory.common.response.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import seongmin.ministory.common.response.ErrorResponse;
import seongmin.ministory.common.response.exception.*;

import java.nio.file.AccessDeniedException;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(GlobalErrorException.class)
    protected ResponseEntity<ErrorResponse> handleGlobalErrorException(GlobalErrorException e) {
        log.warn("handleGlobalErrorException : {}", e.getMessage());
        final ErrorResponse response = ErrorResponse.of(e.getMessage());
        return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(response);
    }

    @ExceptionHandler(ContentErrorException.class)
    protected ResponseEntity<ErrorResponse> handleContentErrorException(ContentErrorException e) {
        log.warn("handleContentErrorException : {}", e.getMessage());
        final ErrorResponse response = ErrorResponse.of(e.getMessage());
        return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(response);
    }

    @ExceptionHandler(CommentErrorException.class)
    protected ResponseEntity<ErrorResponse> handleCommentErrorException(CommentErrorException e) {
        log.warn("handleCommentErrorException : {}", e.getMessage());
        final ErrorResponse response = ErrorResponse.of(e.getMessage());
        return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(response);
    }

    @ExceptionHandler(TagErrorException.class)
    protected ResponseEntity<ErrorResponse> handleTagErrorException(TagErrorException e) {
        log.warn("handleTagErrorException : {}", e.getMessage());
        final ErrorResponse response = ErrorResponse.of(e.getMessage());
        return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(response);
    }

    @ExceptionHandler(UserErrorException.class)
    protected ResponseEntity<ErrorResponse> handleUserErrorException(UserErrorException e) {
        log.warn("handleUserErrorException : {}", e.getMessage());
        final ErrorResponse response = ErrorResponse.of(e.getMessage());
        return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(response);
    }

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<ErrorResponse> handlerAccessDeniedException(AccessDeniedException e) {
        log.warn("handlerAccessDeniedException : {}", e.getMessage());
        final ErrorResponse response = ErrorResponse.of(e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
}
