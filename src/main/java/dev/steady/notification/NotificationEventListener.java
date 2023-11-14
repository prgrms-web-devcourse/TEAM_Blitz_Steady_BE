package dev.steady.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static dev.steady.notification.NotificationType.*;

@Component
@RequiredArgsConstructor
public class NotificationEventListener {

    private final NotificationRepository notificationRepository;

    @EventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void sendNotification(NotificationEvent event) {
        if (event.type().equals(RECRUITMENT)) {
            String content = NotificationMessage.getRecruitmentMessage("gd");
            String redirectUri;
        }


    }

}
