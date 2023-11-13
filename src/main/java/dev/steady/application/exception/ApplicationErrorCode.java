package dev.steady.application.exception;

import dev.steady.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ApplicationErrorCode implements ErrorCode {

    APPLICATION_NOT_FOUND("AP01", "신청서를 찾을 수 없습니다."),
    APPLICATION_AUTH_FAILURE("AP02", "신청서에 접근 권한이 없습니다.");

    private final String errorCode;
    private final String message;

    @Override
    public String code() {
        return this.errorCode;
    }

    @Override
    public String message() {
        return this.message;
    }

}
