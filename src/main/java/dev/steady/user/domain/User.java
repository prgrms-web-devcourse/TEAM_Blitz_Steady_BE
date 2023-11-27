package dev.steady.user.domain;

import dev.steady.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "users")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    private static final String DEFAULT_PROFILE_IMAGE_URL = "https://github.com/Team-Blitz-Steady/steady-server/assets/102007066/18b7281d-255c-474d-a0c3-1e4909862a1f";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String profileImage;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = true)
    private String bio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id")
    private Position position;

    @Column(nullable = false)
    private boolean isDeleted;

    @Builder
    private User(String profileImage, String nickname, String bio, Position position) {
        this.profileImage = profileImage;
        this.nickname = nickname;
        this.bio = bio;
        this.position = position;
        this.isDeleted = false;
    }

    public User(String nickname, Position position) {
        this.nickname = nickname;
        this.position = position;
        this.profileImage = DEFAULT_PROFILE_IMAGE_URL;
        this.isDeleted = false;
    }

    public void update(String profileImage,
                       String nickname,
                       String bio,
                       Position position) {
        this.profileImage = profileImage;
        this.nickname = nickname;
        this.bio = bio;
        this.position = position;
    }

    public void withdraw() {
        this.profileImage = DEFAULT_PROFILE_IMAGE_URL;
        this.nickname = WithdrawUserUtils.generateWithdrawName();
        this.bio = null;
        this.position = null;
        this.isDeleted = true;
    }

}
