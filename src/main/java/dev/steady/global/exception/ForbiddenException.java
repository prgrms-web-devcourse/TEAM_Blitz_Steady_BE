package dev.steady.global.exception;

public class ForbiddenException extends BusinessException{

    private final ErrorCode errorCode;

    public ForbiddenException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

}
