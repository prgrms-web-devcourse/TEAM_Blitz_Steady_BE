package dev.steady.notification.domain.repository;

import dev.steady.global.exception.NotFoundException;
import dev.steady.notification.domain.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import static dev.steady.notification.exception.NotificationErrorCode.NOTIFICATION_NOT_FOUND;

public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {

    List<NotificationEntity> findByReceiverId(Long receiverId);

    void deleteByReceiverId(Long receiverId);

    default NotificationEntity getById(Long notificationId) {
        return findById(notificationId)
                .orElseThrow(() -> new NotFoundException(NOTIFICATION_NOT_FOUND));
    }

}
