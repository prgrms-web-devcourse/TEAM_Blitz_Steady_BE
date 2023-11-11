package dev.steady.user.exception;

import dev.steady.global.exception.ErrorCode;

public enum PositionErrorCode implements ErrorCode {

    POSITION_NOT_FOUND("P100", "해당 포지션을 찾을 수 없습니다.");

    private final String code;
    private final String message;

    PositionErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }

}
