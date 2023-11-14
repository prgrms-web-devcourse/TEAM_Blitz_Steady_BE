package dev.steady.notification;

import dev.steady.user.domain.User;

public record NotificationEvent(
        User receiver,
        NotificationType type
) {

    public static NotificationEvent createRecruitmentNotification(User receiver) {
        return new NotificationEvent(receiver, NotificationType.RECRUITMENT);
    }

//    public static NotificationEvent createRecruitmentNotification(User receiver) {
//        return new NotificationEvent(receiver, NotificationType.RECRUITMENT);
//    }

}
