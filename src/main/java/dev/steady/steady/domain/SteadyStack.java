package dev.steady.steady.domain;

import dev.steady.global.entity.BaseEntity;
import dev.steady.user.domain.Stack;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "steady_stacks")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SteadyStack extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stack_id")
    private Stack stack;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "steady_id")
    private Steady steady;

    public SteadyStack(Stack stack, Steady steady) {
        this.stack = stack;
        this.steady = steady;
    }

}
