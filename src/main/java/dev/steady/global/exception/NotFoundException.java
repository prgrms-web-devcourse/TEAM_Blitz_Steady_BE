package dev.steady.global.exception;

public class NotFoundException extends BusinessException{

    private final ErrorCode errorCode;

    public NotFoundException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

}
