package dev.steady.global.exception;

public class AuthenticationException extends BusinessException{

    private final ErrorCode errorCode;

    public AuthenticationException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

}
