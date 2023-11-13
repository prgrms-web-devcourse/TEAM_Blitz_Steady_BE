package dev.steady.template.exception;

import dev.steady.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TemplateErrorCode implements ErrorCode {

    TEMPLATE_NOT_FOUND("T001", "템플릿을 찾을 수 없습니다."),
    TEMPLATE_AUTH_FAILURE("T002", "템플릿에 접근 권한이 없습니다.");

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
