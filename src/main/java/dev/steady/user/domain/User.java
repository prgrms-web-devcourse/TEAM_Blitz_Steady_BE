package dev.steady.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String profileImage;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = true)
    private String bio;

    @Builder
    private User(
            String profileImage,
            String nickname,
            String bio) {
        this.profileImage = profileImage;
        this.nickname = nickname;
        this.bio = bio;
    }

}
