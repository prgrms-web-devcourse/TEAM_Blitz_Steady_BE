package dev.steady.notification.domain;

import dev.steady.application.domain.Application;
import dev.steady.application.domain.ApplicationStatus;
import dev.steady.steady.domain.Steady;
import dev.steady.user.domain.User;

public class ApplicationResultNotificationStrategy extends NotificationStrategy {

    private final Steady steady;
    private final ApplicationStatus status;

    public ApplicationResultNotificationStrategy(Application application) {
        super(application.getUser(), NotificationType.APPLICATION_RESULT);
        this.steady = application.getSteady();
        this.status = application.getStatus();
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
