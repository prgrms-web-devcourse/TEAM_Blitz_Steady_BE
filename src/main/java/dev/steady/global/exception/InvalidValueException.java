package dev.steady.global.exception;

public class InvalidValueException extends BusinessException {

    private final ErrorCode errorCode;

    public InvalidValueException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

}
