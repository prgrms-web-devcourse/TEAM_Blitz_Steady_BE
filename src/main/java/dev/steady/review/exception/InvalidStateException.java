package dev.steady.review.exception;

import dev.steady.global.exception.BusinessException;
import dev.steady.global.exception.ErrorCode;

public class InvalidStateException extends BusinessException {

    private final ErrorCode errorCode;

    public InvalidStateException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

}
