package dev.steady.notification.domain;

import dev.steady.global.entity.BaseEntity;
import dev.steady.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "notifications")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private NotificationType type;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String redirectUri;

    @Column(nullable = false)
    private boolean isRead;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User receiver;

    @Builder
    private Notification(NotificationType type, String content, String redirectUri, User receiver) {
        this.type = type;
        this.content = content;
        this.redirectUri = redirectUri;
        this.isRead = false;
        this.receiver = receiver;
    }

    public void read() {
        this.isRead = true;
    }

}
