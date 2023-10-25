package dev.steady.steady.domain;

import dev.steady.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "steady_likes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SteadyLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Steady steady;

    @Builder
    private SteadyLike(User user, Steady steady) {
        this.user = user;
        this.steady = steady;
    }

}
