package dev.steady.notification.domain;

import dev.steady.application.domain.ApplicationStatus;
import dev.steady.steady.domain.Steady;
import dev.steady.user.domain.User;

public class ApplicationResultNotification extends Notification {

    private final Steady steady;
    private final ApplicationStatus status;

    public ApplicationResultNotification(User receiver, Steady steady, ApplicationStatus status) {
        super(receiver, NotificationType.APPLICATION_RESULT);
        this.steady = steady;
        this.status = status;
    }

    @Override
    public String getMessage() {
        return NotificationMessage.getApplicationResultMessage(steady.getName(), status);
    }

    @Override
    public String getRedirectUri() {
        return String.format("/steady/applicant/%d", steady.getId());
    }

}
