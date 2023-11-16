package dev.steady.notification.controller;

import dev.steady.global.auth.Auth;
import dev.steady.global.auth.UserInfo;
import dev.steady.notification.dto.NotificationsResponse;
import dev.steady.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public ResponseEntity<NotificationsResponse> getNotifications(@Auth UserInfo userInfo) {
        NotificationsResponse response = notificationService.getNotifications(userInfo);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{notificationId}")
    public ResponseEntity<Void> readNotification(@PathVariable Long notificationId,
                                                 @Auth UserInfo userInfo) {
        notificationService.readNotification(notificationId, userInfo);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/readAll")
    public ResponseEntity<Void> readNotifications(@Auth UserInfo userInfo) {
        notificationService.readNotifications(userInfo);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{notificationId}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long notificationId,
                                                   @Auth UserInfo userInfo) {
        notificationService.deleteNotification(notificationId, userInfo);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<Void> deleteAll(@Auth UserInfo userInfo) {
        notificationService.deleteAll(userInfo);
        return ResponseEntity.noContent().build();
    }

}
