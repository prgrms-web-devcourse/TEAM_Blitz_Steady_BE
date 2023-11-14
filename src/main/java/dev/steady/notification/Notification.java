package dev.steady.notification;

import dev.steady.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static dev.steady.notification.NotificationType.APPLICATION;
import static dev.steady.notification.NotificationType.CHAT;
import static dev.steady.notification.NotificationType.COMMENT;
import static dev.steady.notification.NotificationType.RECRUITMENT;

@Entity
@Getter
@Table(name = "notifications")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private NotificationType type;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String redirectUri;

    @Column(nullable = false)
    private boolean isRead;

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "user_id")
    private User receiver;

    private Notification(User receiver, NotificationType type) {
        this.receiver = receiver;
        this.type = type;
    }

    public static Notification createRecruitNotification(User receiver) {
        return new Notification(receiver, RECRUITMENT);
    }

    public static Notification createApplicationNotification(User receiver) {
        return new Notification(receiver, APPLICATION);
    }

}
