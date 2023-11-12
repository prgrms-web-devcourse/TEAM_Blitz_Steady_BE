package dev.steady.steady.exception;

import dev.steady.global.exception.ErrorCode;

public enum ParticipantErrorCode implements ErrorCode {

    PARTICIPANT_NOT_FOUND("P001", "스테디 참여자를 찾을 수 없습니다.");

    private final String code;
    private final String message;

    ParticipantErrorCode(String code, String message) {
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
