package dev.steady.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@EqualsAndHashCode
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "platform", nullable = false)
    private Platform platform;

    @Column(name = "platform_id", nullable = false)
    private String platform_id;

    @Column(name = "profile_image", nullable = false)
    private String profileImage;

    @Column(name = "nickname", nullable = false, unique = true)
    private String nickname;

    @Column(name = "bio", nullable = true)
    private String bio;

    @Column(name = "is_deleted", nullable = true)
    private LocalDateTime isDeleted;

    @Builder
    private User(String platform_id,
                Platform platform,
                String profileImage,
                String nickname,
                String bio,
                LocalDateTime isDeleted) {
        this.platform_id = platform_id;
        this.platform = platform;
        this.profileImage = profileImage;
        this.nickname = nickname;
        this.bio = bio;
        this.isDeleted = isDeleted;
    }
}
