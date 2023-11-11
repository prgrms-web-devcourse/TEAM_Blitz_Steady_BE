package dev.steady.user.exception;

import dev.steady.global.exception.ErrorCode;

public enum StackErrorCode implements ErrorCode {

    STACK_NOT_FOUND("ST100", "해당 스택을 찾을 수 없습니다.");

    private final String code;
    private final String message;

    StackErrorCode(String code, String message) {
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
