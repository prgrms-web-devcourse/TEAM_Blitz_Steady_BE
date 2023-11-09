package dev.steady.global.exception;

public class ForbiddenException extends BusinessException{

    private final ErrorCode errorCode;

    public ForbiddenException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

}
