package dev.steady.steady.exception;

import dev.steady.global.exception.BusinessException;
import dev.steady.global.exception.ErrorCode;

public class LeaderPermissionNeededException extends BusinessException {

    public LeaderPermissionNeededException(ErrorCode errorCode) {
        super(errorCode);
    }

}
