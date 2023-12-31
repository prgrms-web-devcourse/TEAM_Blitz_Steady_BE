package dev.steady.notification.domain;

import dev.steady.steady.domain.Steady;

public class FreshApplicationNotificationStrategy extends NotificationStrategy {

    private final Steady steady;

    public FreshApplicationNotificationStrategy(Steady steady) {
        super(steady.getLeader(), NotificationType.FRESH_APPLICATION);
        this.steady = steady;
    }

    @Override
    public String getMessage() {
        return NotificationMessage.getFreshApplicationMessage(steady.getName());
    }

    @Override
    public String getRedirectUri() {
        return String.format("/steady/applicant/%d", steady.getId());
    }

}
