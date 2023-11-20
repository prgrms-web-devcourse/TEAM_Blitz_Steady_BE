package dev.steady.global.advice;

import dev.steady.auth.exception.OAuthPlatformException;
import dev.steady.global.exception.*;
import dev.steady.global.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handlerException(Exception e) {
        log.info("예상치 못한 서버 예외 발생", e);
        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.of(GlobalErrorCode.INTERNAL_SERVER_ERROR));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handlerException(AuthenticationException authenticationException) {
        ErrorCode errorCode = authenticationException.getErrorCode();
        log.info("{}", errorCode.message());
        return ResponseEntity.status(UNAUTHORIZED)
                .body(ErrorResponse.of(authenticationException.getErrorCode()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handlerException(ForbiddenException forbiddenException) {
        ErrorCode errorCode = forbiddenException.getErrorCode();
        log.info("{}", errorCode.message());
        return ResponseEntity.status(FORBIDDEN)
                .body(ErrorResponse.of(forbiddenException.getErrorCode()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handlerException(NotFoundException notFoundException) {
        ErrorCode errorCode = notFoundException.getErrorCode();
        log.info("{}", errorCode.message());
        return ResponseEntity.badRequest()
                .body(ErrorResponse.of(notFoundException.getErrorCode()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handlerException(InvalidStateException invalidStateException) {
        ErrorCode errorCode = invalidStateException.getErrorCode();
        log.info("{}", errorCode.message());
        return ResponseEntity.badRequest()
                .body(ErrorResponse.of(invalidStateException.getErrorCode()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handlerException(InvalidValueException invalidValueException) {
        ErrorCode errorCode = invalidValueException.getErrorCode();
        log.info("{}", errorCode.message());
        return ResponseEntity.badRequest()
                .body(ErrorResponse.of(invalidValueException.getErrorCode()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handlerException(OAuthPlatformException OAuthPlatformException) {
        ErrorCode errorCode = OAuthPlatformException.getErrorCode();
        log.info("{}", errorCode.message());
        return ResponseEntity.badRequest()
                .body(ErrorResponse.of(OAuthPlatformException.getErrorCode()));
    }

}
