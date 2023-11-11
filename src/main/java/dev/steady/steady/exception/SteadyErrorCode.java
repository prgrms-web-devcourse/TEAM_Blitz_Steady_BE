package dev.steady.steady.exception;

import dev.steady.global.exception.ErrorCode;

public enum SteadyErrorCode implements ErrorCode {

    STEADY_NOT_FOUND("S100", "해당 Steady를 찾을 수 없습니다."),
    STEADY_IS_NOT_EMPTY("S101", "Steady에 리더를 제외한 참여자가 존재합니다."),
    LEADER_PERMISSION_NEEDED("S102", "Steady 리더 권한이 필요합니다."),
    PROMOTION_COUNT_EXCEPTION("S103", "제공된 끌어올리기를 모두 사용하였습니다."),
    PARTICIPANT_LIMIT_EXCEEDED("S104", "Steady 정원을 초과할 수 없습니다.");

    private final String code;
    private final String message;

    SteadyErrorCode(String code, String message) {
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
