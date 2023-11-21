package dev.steady.application.exception;

import dev.steady.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SurveyResultErrorCode implements ErrorCode {

    INVALID_SURVEY_SIZE("SU00", "신청서에 접근 권한이 없습니다.");

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
