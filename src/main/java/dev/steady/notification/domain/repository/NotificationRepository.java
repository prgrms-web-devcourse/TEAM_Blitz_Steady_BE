package dev.steady.notification.domain.repository;

import dev.steady.global.exception.NotFoundException;
import dev.steady.notification.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import static dev.steady.notification.exception.NotificationErrorCode.NOTIFICATION_NOT_FOUND;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByReceiverId(Long receiverId);

    void deleteByReceiverId(Long receiverId);

    default Notification getById(Long notificationId) {
        return findById(notificationId)
                .orElseThrow(() -> new NotFoundException(NOTIFICATION_NOT_FOUND));
    }

}
