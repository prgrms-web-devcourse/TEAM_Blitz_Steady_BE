package dev.steady.steady.domain;

import dev.steady.user.domain.Position;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "steady_positions")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SteadyPosition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "position_id")
    private Position position;

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "steady_id")
    private Steady steady;

    @Builder
    private SteadyPosition(Position position, Steady steady) {
        this.position = position;
        this.steady = steady;
    }

}
