package dev.steady.notification.domain;

import dev.steady.application.domain.ApplicationStatus;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum NotificationMessage {

    FRESH_APPLICATION("[%s] 스테디에 새로운 신청서가 도착했어요."),
    APPLICATION_ACCEPTED("[%s] 스터디에 제출한 신청서가 수락됐어요."),
    APPLICATION_REJECTED("[%s] 스터디에 제출한 신청서가 거절됐어요."),
    ;

    private final String message;

    public static String getFreshApplicationMessage(String steadyName) {
        return String.format(FRESH_APPLICATION.message, steadyName);
    }

    public static String getApplicationResultMessage(String steadyName, ApplicationStatus status) {
        if (status.equals(ApplicationStatus.ACCEPTED)) {
            return String.format(APPLICATION_ACCEPTED.message, steadyName);
        }
        return String.format(APPLICATION_REJECTED.message, steadyName);
    }

}
