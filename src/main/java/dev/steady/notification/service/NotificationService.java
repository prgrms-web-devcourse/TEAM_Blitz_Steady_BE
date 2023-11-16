package dev.steady.notification.service;

import dev.steady.global.auth.UserInfo;
import dev.steady.notification.domain.Notification;
import dev.steady.notification.domain.NotificationEntity;
import dev.steady.notification.domain.repository.NotificationRepository;
import dev.steady.notification.dto.NotificationsResponse;
import dev.steady.user.domain.User;
import dev.steady.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void create(Notification notification) {
        NotificationEntity entity = notification.toEntity();
        notificationRepository.save(entity);
    }

    @Transactional(readOnly = true)
    public NotificationsResponse getNotifications(UserInfo userInfo) {
        User receiver = userRepository.getUserBy(userInfo.userId());
        List<NotificationEntity> notifications = notificationRepository.findByReceiverId(receiver.getId());
        return NotificationsResponse.from(notifications);
    }

    @Transactional
    public void readNotification(Long notificationId, UserInfo userInfo) {
        userRepository.getUserBy(userInfo.userId());
        NotificationEntity notification = notificationRepository.getById(notificationId);
        notification.read();
    }

    @Transactional
    public void readNotifications(UserInfo userInfo) {
        User receiver = userRepository.getUserBy(userInfo.userId());
        List<NotificationEntity> notifications = notificationRepository.findByReceiverId(receiver.getId());
        notifications.forEach(v -> v.read());
    }

    @Transactional
    public void deleteNotification(Long notificationId, UserInfo userInfo) {
        userRepository.getUserBy(userInfo.userId());
        notificationRepository.deleteById(notificationId);
    }

    @Transactional
    public void deleteAll(UserInfo userInfo) {
        User receiver = userRepository.getUserBy(userInfo.userId());
        notificationRepository.deleteByReceiverId(receiver.getId());
    }

}
