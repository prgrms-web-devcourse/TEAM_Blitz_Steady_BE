package dev.steady.steady.exception;

import dev.steady.global.exception.BusinessException;
import dev.steady.global.exception.ErrorCode;

public class PromotionCountException extends BusinessException {

    private final ErrorCode errorCode;

    public PromotionCountException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

}
