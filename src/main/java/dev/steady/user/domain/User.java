package dev.steady.user.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@EqualsAndHashCode
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id")
    private Position position;

    @Builder
    public User(String profileImage, String nickname, String bio, Position position) {
        this.profileImage = profileImage;
        this.nickname = nickname;
        this.bio = bio;
        this.position = position;
    }

}
