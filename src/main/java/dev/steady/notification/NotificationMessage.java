package dev.steady.notification;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum NotificationMessage {

    RECRUITMENT_MESSAGE("%s 스터디에 새로운 신청서가 도착했어요.");

    private final String message;

    public static String getRecruitmentMessage(String steadyName) {
        return String.format(RECRUITMENT_MESSAGE.message, steadyName);
    }

}
