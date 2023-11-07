package dev.steady.global.exception;

public class NotFoundException extends BusinessException{

    private final ErrorCode errorCode;

    public NotFoundException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

}
