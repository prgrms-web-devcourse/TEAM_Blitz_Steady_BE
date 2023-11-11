package dev.steady.steady.exception;

import dev.steady.global.exception.BusinessException;
import dev.steady.global.exception.ErrorCode;

public class ParticipantLimitExceededException extends BusinessException {

    private final ErrorCode errorCode;

    public ParticipantLimitExceededException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

}
