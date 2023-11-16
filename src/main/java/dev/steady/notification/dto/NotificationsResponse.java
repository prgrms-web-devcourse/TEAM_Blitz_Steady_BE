package dev.steady.notification.dto;

import dev.steady.notification.domain.Notification;

import java.util.List;

public record NotificationsResponse(
        List<NotificationResponse> notifications,
        long freshCount
) {

    public static NotificationsResponse from(List<Notification> notifications) {
        List<NotificationResponse> response = notifications.stream()
                .map(NotificationResponse::from)
                .toList();

        long freshCount = response.stream()
                .filter(v -> !v.isRead())
                .count();
        return new NotificationsResponse(response, freshCount);
    }

}
