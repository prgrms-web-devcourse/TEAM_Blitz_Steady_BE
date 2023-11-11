package dev.steady.steady.exception;

import dev.steady.global.exception.BusinessException;
import dev.steady.global.exception.ErrorCode;

public class LeaderPermissionNeededException extends BusinessException {

    private final ErrorCode errorCode;

    public LeaderPermissionNeededException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

}
