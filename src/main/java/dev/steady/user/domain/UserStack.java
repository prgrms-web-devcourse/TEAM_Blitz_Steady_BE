package dev.steady.user.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "user_stack")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserStack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Stack stack;

    @Builder
    private UserStack(User user, Stack stack) {
        this.user = user;
        this.stack = stack;
    }

}