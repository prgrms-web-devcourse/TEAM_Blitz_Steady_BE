package dev.steady.steady.exception;

import dev.steady.global.exception.BusinessException;
import dev.steady.global.exception.ErrorCode;

public class SteadyIsNotEmptyException extends BusinessException {

    public SteadyIsNotEmptyException(ErrorCode errorCode) {
        super(errorCode);
    }

}
