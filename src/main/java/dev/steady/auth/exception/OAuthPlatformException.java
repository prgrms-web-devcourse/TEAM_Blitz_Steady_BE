package dev.steady.auth.exception;

import dev.steady.global.exception.BusinessException;
import dev.steady.global.exception.ErrorCode;

public class OAuthPlatformException extends BusinessException {

    public OAuthPlatformException(ErrorCode errorCode) {
        super(errorCode);
    }

}
