package dev.steady.notification.domain;

import dev.steady.steady.domain.Steady;
import dev.steady.user.domain.User;

public class FreshApplicationNotification extends Notification {

    private final Steady steady;

    public FreshApplicationNotification(User receiver, Steady steady) {
        super(receiver, NotificationType.FRESH_APPLICATION);
        this.steady = steady;
    }

    @Override
    public String getMessage() {
        return NotificationMessage.getFreshApplicationMessage(steady.getName());
    }

    @Override
    public String getRedirectUri() {
        return String.format("/steady/detail/%d", steady.getId());
    }

}
