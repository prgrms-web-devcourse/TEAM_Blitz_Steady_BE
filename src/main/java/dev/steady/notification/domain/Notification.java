package dev.steady.notification.domain;

import dev.steady.application.domain.Application;
import dev.steady.steady.domain.Steady;
import dev.steady.user.domain.User;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Notification {

    private final User receiver;
    private final NotificationType type;

    public static Notification createFreshApplicationNoti(Steady steady) {
        return new FreshApplicationNotification(steady.getLeader(), steady);
    }

    public static Notification createApplicationResultNoti(Application application) {
        return new ApplicationResultNotification(application.getUser(), application.getSteady(), application.getStatus());
    }

    public abstract String getMessage();

    public abstract String getRedirectUri();

    public NotificationEntity toEntity() {
        return NotificationEntity.builder()
                .type(type)
                .content(getMessage())
                .redirectUri(getRedirectUri())
                .receiver(receiver)
                .build();
    }

}
