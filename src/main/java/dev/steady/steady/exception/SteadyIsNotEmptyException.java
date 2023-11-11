package dev.steady.steady.exception;

import dev.steady.global.exception.BusinessException;
import dev.steady.global.exception.ErrorCode;

public class SteadyIsNotEmptyException extends BusinessException {

    private final ErrorCode errorCode;

    public SteadyIsNotEmptyException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

}
