package dev.steady.global.exception;

public class InvalidStateException extends BusinessException {

    private final ErrorCode errorCode;

    public InvalidStateException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

}
