package dev.steady.steady.exception;

import dev.steady.global.exception.BusinessException;
import dev.steady.global.exception.ErrorCode;

public class ParticipantLimitExceededException extends BusinessException {

    public ParticipantLimitExceededException(ErrorCode errorCode) {
        super(errorCode);
    }

}
