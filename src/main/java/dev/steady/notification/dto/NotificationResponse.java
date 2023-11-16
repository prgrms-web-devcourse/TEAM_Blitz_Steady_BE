package dev.steady.notification.dto;

import dev.steady.notification.domain.Notification;
import dev.steady.notification.domain.NotificationType;

public record NotificationResponse(
        Long id,
        NotificationType type,
        String content,
        String redirectUri,
        boolean isRead
) {

    public static NotificationResponse from(Notification entity) {
        return new NotificationResponse(entity.getId(),
                entity.getType(),
                entity.getContent(),
                entity.getRedirectUri(),
                entity.isRead());
    }

}
