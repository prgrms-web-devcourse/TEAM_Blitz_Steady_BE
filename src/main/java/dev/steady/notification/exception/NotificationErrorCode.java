package dev.steady.notification.exception;

import dev.steady.global.exception.ErrorCode;

public enum NotificationErrorCode implements ErrorCode {

    NOTIFICATION_NOT_FOUND("N001", "존재하지 않는 알림입니다.");

    private final String code;
    private final String message;

    NotificationErrorCode(String code, String message) {
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
