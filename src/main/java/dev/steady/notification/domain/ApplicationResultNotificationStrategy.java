package dev.steady.notification.domain;

import dev.steady.application.domain.Application;
import dev.steady.application.domain.ApplicationStatus;
import dev.steady.steady.domain.Steady;

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
        if (status == ApplicationStatus.ACCEPTED) {
            return "/mysteady";
        }
        return "/mypage/application";
    }

}
