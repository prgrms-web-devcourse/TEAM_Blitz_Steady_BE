package dev.steady.notification.fixture;

import dev.steady.notification.domain.Notification;
import dev.steady.notification.domain.NotificationType;
import dev.steady.notification.dto.NotificationResponse;
import dev.steady.notification.dto.NotificationsResponse;
import dev.steady.user.domain.User;

import java.util.List;

public class NotificationFixture {

    public static Notification createFreshApplicationNoti(User receiver) {
        return Notification.builder()
                .type(NotificationType.FRESH_APPLICATION)
                .content("알림 내용")
                .redirectUri("리다이렉트 uri")
                .receiver(receiver)
                .build();
    }

    public static Notification createApplicationResultNoti(User receiver) {
        return Notification.builder()
                .type(NotificationType.APPLICATION_RESULT)
                .content("알림 내용")
                .redirectUri("리다이렉트 uri")
                .receiver(receiver)
                .build();
    }

    public static NotificationsResponse createNotificationsResponse() {
        return new NotificationsResponse(List.of(
                new NotificationResponse(1L, NotificationType.FRESH_APPLICATION, "내용", "리다이렉트 uri", false),
                new NotificationResponse(1L, NotificationType.FRESH_APPLICATION, "내용", "리다이렉트 uri", true)
        ), 1L);
    }

}
