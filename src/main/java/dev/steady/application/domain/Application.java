package dev.steady.application.domain;

import dev.steady.steady.domain.Steady;
import dev.steady.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "applications")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Steady steady;

    @Column
    @Enumerated(EnumType.STRING)
    private ApplicationStatus applicationStatus;
    // TODO: 2023-10-22 영경님 이거 네이밍 하십쇼 (패키지명, 도메인명)


}
