package dev.steady.notification.domain;

import dev.steady.user.domain.User;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class NotificationStrategy {

    private final User receiver;
    private final NotificationType type;

    public abstract String getMessage();

    public abstract String getRedirectUri();

    public Notification toEntity() {
        return Notification.builder()
                .type(type)
                .content(getMessage())
                .redirectUri(getRedirectUri())
                .receiver(receiver)
                .build();
    }

}
