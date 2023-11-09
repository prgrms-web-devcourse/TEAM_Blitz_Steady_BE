package dev.steady.steady.exception;

import dev.steady.global.exception.BusinessException;
import dev.steady.global.exception.ErrorCode;

public class PromotionCountException extends BusinessException {

    public PromotionCountException(ErrorCode errorCode) {
        super(errorCode);
    }

}
